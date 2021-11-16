function getTrack(fetch, deliveryNo) {
  console.log("gettrack 함수 시작");
  const _url =
    "https://service.epost.go.kr/iservice/usr/trace/usrtrc001k01.jsp";
  const _options = {
    method: "POST",
    mode: "cors",
    header: {
      Accept: "application/json",
      "Content-Type": "application/json; charset: UTP-8",
    },
    body: JSON.stringify({
      sid1: deliveryNo,
    }),
  };
  return fetch(_url, _options).then((res) => res);
}

module.exports = {
  getTrack,
};
