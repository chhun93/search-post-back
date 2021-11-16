const kPostOffice = require("./post-list/k-post-office");
const http = require("http");
const PORT = 3030;

const server = http.createServer((req, res) => {
  const splitUrl = req.url.split("/");

  if (1 < splitUrl.length) res.setHeader("Access-Control-Allow-Origin", "*");
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
      console.log(kPostOffice.getTrack(splitUrl[2]));
      setTimeout(() => {
        res.end(`{"'GET'": "${splitUrl[2]}"}`);
      }, 5000);
      break;
    default:
      res.statusCode = 404;
      res.end("NOT FOUND");
      break;
  }
});

server.listen(process.env.PORT || PORT, () => console.log("Server is opened."));
