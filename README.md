# MoocFi Project Cyber Security Base - Project 1

This is a **Spring Boot** application made in partial fulfilment of the course Cyber Security Base by MoocFi.

## Running Instructions

- `$ git clone https://github.com/h4ck3rk3y/moocfriproject1`
- `$ cd moocfriproject1`
- `$ mvn install -e`
- `$ java -jar target/Project-1.0-SNAPSHOT.jar`
- Visit http://localhost:8080 on your browser

The following username and password combinations work

- user:roger password:carrots
- user:valiant password:vaudeville

The following vulnerabilities exist in the application. I've also listed outways to fix them.

### Issue 1: XSS
#### Effect: Execute malicious javascript by making new posts
#### Steps to Replicate

- Go to http://localhost:8080
- Login with either of the username & password combination
- Make a new post with the following content
    + title: First Post
    + content: `<script>alert("XSS");</script>`
    + public: checked
- Refresh the page and you will be greeted by an alert window

#### How to fix
- Edit posts.html and change th:utext to th:text wherever necessary

### Issue 2: Insecure Direct Object Reference
#### Effect: Access any users private posts
#### Steps to Replicate

- Go to http://localhost:8080
- Login with either of the username & password combination
- Click on your posts
- Now change your username in the url to the other valid username
- Press enter and you can see both public and private posts of that user

#### How to fix
- Replace the URL parameter with mechanism to get the current username from the authentication session. You can use `authentication.getName()` instead of relying on username in the url.

### Issue 3: Missing Function Level Access Control
#### Effect: Attacker can post as another user
#### Steps to Replicate

- Go to http://localhost:8080
- Login with either of the username & password combination
- Ensure that Burp is running on intercept mode
- Make a new post and hit add.
- Look at the POST request that is intercepted and add a value to the postedBy field(which was hidden, maybe the developers earlier had this and then decided to use sessions instead).
- Forward the editted request. Switch off Interception Mode.
- You can see that your post appears to be from a different user.

#### How to Fix


- Remove the postedBy parameter completely and only use the `authentication.username()` feature that exists already.

### Issue 4: Security Misconfiguration & CSRF
#### Effect: A security misconfiguration allows for CSRF
#### Steps to Replicate:

- Go to http://localhost:8080
- Login with either of the username & password combination
- Make a new post with the following content
    + title: First Post
    + content: Wrap Vector-1 in `<script></script>` tags
    + public: checked
- Now whoever visits the /posts page will post something to that page.

Vector-1
~~~javascript
var http = new XMLHttpRequest();
var url = "http://localhost:8080/post/";
var params = "title=haha&content=lololol&public=1";
http.open("POST", url, true);

//Send the proper header information along with the request
http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

http.onreadystatechange = function() {//Call a function when the state changes.
    if(http.readyState == 4 && http.status == 200) {
        alert("h4x0r y0u h4v3 b33n c0mpr1m1s3d");
    }
}
http.send(params);
~~~

#### How to Fix
- Fix the XSS vulnerability listed above. Further remove `http.csrf().disable();` from SecurityConfiguration.java.

### Issue 5: Sensitive Data Exposure & Broken Authentication and Session Management
#### Effect: A CustomPasswordEncoder has been used that simply concatenates the ASCII value of the characters. If a breach occurs then passwords can be easily reversed to plaintext.
#### Steps to Replicate

- Open the CodeBase
- Visit SecurityConfiguration.java
- On line 39 you'll notice that a `CustomPasswordEncoder()` has been initialized
- Invistigate the relevant java file and you'll see that the encoding is very week.

#### How To Fix
- Remove CustomPasswordEncoder.java and in SecurityConfiguration.java replace `CustomPasswordEncoder();` with `BCryptPasswordEncoder()`. Remove the import statement for `CustomPasswordEncoder`.