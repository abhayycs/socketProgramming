
Jhttp.java and Jhttpclient.java

Jhttp.java program is used to create a socket based server with default port number 80 and at localhost.
It takes 2 arguments 1st: root directory and 2nd port number
Multiple client can send request to the Jhttp server. This thing is handled by java multithreading and client will be considered as an individual thread.
It takes request in GET /filename HTTP version format.
Eg. GET /index.html HTTP/1.1
Accordingly it give response the client.

Jhttpclient.java program is used to connect with Jhttp server default address is localhost and port number is 80
It sends request to the Jhttp server in form of <method_name> /<filename> <http_version>
Eg. GET /song.mp3 HTTP/1.0
