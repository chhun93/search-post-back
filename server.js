const kPostOffice = require("./post-list/k-post-office");
const http = require("http");
const PORT = 3030;

const server = http.createServer((req, res) => {
  const splitUrl = req.url.split("/");

  res.setHeader("Access-Control-Allow-Origin", "*");
  switch (true) {
    case "/" === req.url:
      res.statusCode = 200;
      res.end("POST SERVICE");
      break;
    case 2 === splitUrl.length && "lists" === splitUrl[1]:
      res.statusCode = 200;
      setTimeout(() => {
        res.end(`{"GET": "URL LISTS"}`);
      }, 5000);
      break;
    case 3 === splitUrl.length:
      res.writeHead(200, { "Content-Type": "application/json" });

      new Promise((resolve, reject) => {
        setTimeout(() => {
          let test_Json;
          if (splitUrl[2] === "1234") {
            let testJsonArray = [];
            testJsonArray.push({ key: "0", id: "cu", state: "READY" });
            testJsonArray.push({ key: "1", id: "우체국", state: "READY" });
            
            test_Json = testJsonArray;
          } else if (0 < splitUrl[2].trim().length) {
            test_Json = [];
          } else reject("error");
          
          res.write(JSON.stringify(test_Json));
          resolve(test_Json);
        }, 1500);
      })
        .then((response) => {
          console.log(response);
          return res.end();
        })
        .catch((err) => {
          return err;
        });

      break;
    default:
      res.statusCode = 404;
      res.end("NOT FOUND");
      break;
  }
});

server.listen(process.env.PORT || PORT, () => console.log("Server is opened."));

