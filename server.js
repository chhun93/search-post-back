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
            testJsonArray.push({
              key: "0",
              id: "0",
              name: "CU",
              state: "GO",
              current: {
                state1: "12.28(화) 18:23-경기일산웨돔",
                state2: "12.28(화) 22:52-곤지암Hub",
                state3: "12.28(화) 22:56-곤지암Hub",
                state4: "12.29(수) 06:36-천안동",
                state5: "12.29(수) 06:39-천안동",
                state6: "12.29(수) 11:31-천안천안하나",
              },
            });
            testJsonArray.push({
              key: "1",
              id: "1",
              name: "우체국",
              state: "READY",
              current: "",
            });

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

