const http = require("http");

const PORT = 3000;
const server = http.createServer((req, res) => {
  const splitUrl = req.url.split("/");

  if (1 < splitUrl.length)
    switch (true) {
      case "/" === req.url:
        res.statusCode = 200;
        res.end("POST SERVICE");
        break;
      case 2 === splitUrl.length && "lists" === splitUrl[1]:
        res.statusCode = 200;
        res.end(`GET lists`);
        break;
      case 3 === splitUrl.length:
        res.statusCode = 200;
        res.end(`GET ${splitUrl[2]}`);
        break;
      default:
        res.statusCode = 404;
        res.end("NOT FOUND");
        break;
    }
});

server.listen(PORT, () => console.log("Server is opened."));
