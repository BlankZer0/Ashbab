const PORT = 8080

var http = require('http');
var app = require('./app');

http.createServer(app.handleRequest).listen(PORT);