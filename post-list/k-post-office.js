const axios = require("axios");

function getTrack(deliveryNo) {
  return new Promise((resolve, reject) => {
    axios
      .get("/lists")
      .then((res) => {
        if (!res.ok) console.log("error!");
        resolve(res);
      })
      .then((data) => {
        resolve(data);
      })
      .catch((err) => {
        console.log("ERROR!! : " + err);
        reject(err);
      });
    // axios
    //   .post("https://service.epost.go.kr/trace.RetrieveDomRigiTraceList.comm", {
    //     sid1: deliveryNo,
    //   })
    //   .then((res) => {
    //     if (!res.ok) console.log("error!");
    //     resolve(res);
    //   })
    //   .then((data) => {
    //     resolve(data);
    //   })
    //   .catch((err) => {
    //     console.log("ERROR!! : " + err);
    //     reject(err);
    //   });
    // {
    //   method: "POST",
    //   mode: "cors",
    //   header: {
    //     Accept: "application/json",
    //     "Content-Type": "application/json; charset: UTP-8",
    //   },
    //   body: JSON.stringify({
    //     'postNum': deliveryNo,
    //   }),
    // }
    //   )
    //   .then((res) => {
    //     if (!res.ok) console.log("error!1");
    //     return res.json();
    //   }).catch(err =>console.log(`ERROR!?!? : ${err}`))
    //   .then((data) => {
    //     return data;
    //   });
  });
}

module.exports = {
  getTrack,
};

