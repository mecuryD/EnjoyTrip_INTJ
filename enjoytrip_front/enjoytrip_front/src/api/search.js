import { localAxios } from "@/util/http-commons";

const local = localAxios();   //axios instance 호출

const url = "/map"

function getSido(success, fail) {
  local.get(`${url}/sido`).then(success).catch(fail);
}

function getGugun(sidoCode, success, fail) {
  local.get(`${url}/gugun/${sidoCode}`).then(success).catch(fail);
}

function searchMap(param, success, fail) {
  console.log(JSON.stringify(param));
  local.post(`${url}`, JSON.stringify(param)).then(success).catch(fail);
}

function mapDetail(contentId, success, fail) {
  local.get(`${url}/${contentId}`).then(success).catch(fail);
}

function doHeart(param, success, fail) {
  local.post(`${url}/heart`, JSON.stringify(param)).then(success).catch(fail);
}

function isHeart(param, success, fail) {
  local.post(`${url}/isheart`, JSON.stringify(param)).then(success).catch(fail);
}

function getHeartList(userId, success, fail) {
  local.get(`${url}/heart/${userId}`).then(success).catch(fail);
}

export {
  getSido,
  getGugun,
  searchMap,
  mapDetail,
  doHeart,
  isHeart,
  getHeartList,
};
