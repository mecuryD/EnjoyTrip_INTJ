import { localAxios } from "@/util/http-commons";

const local = localAxios();   //axios instance 호출

const url = "/user"

async function userConfirm(param, success, fail) {
  console.log("param", param);
  await local.post(`/user/login`, param).then(success).catch(fail);
  console.log("userConfirm ok");
}

async function findById(userid, success, fail) {
  local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
  await local.get(`/user/info/${userid}`).then(success).catch(fail);
}

async function tokenRegeneration(user, success, fail) {
  local.defaults.headers["refreshToken"] = sessionStorage.getItem("refreshToken"); //axios header에 refresh-token 셋팅
  await local.post(`/user/refresh`, user).then(success).catch(fail);
}

function login(param, success, fail) {
  local.post(`${url}/login`, JSON.stringify(param)).then(success).catch(fail);
}

function logout(userId, success, fail) {
  local.get(`${url}/logout/${userId}`).then(success).catch(fail);
}

function join(param, success, fail) {
  console.log('aa', param);
  const obj = JSON.stringify(param);
  console.log("obj", obj);
  local.post(`${url}`, obj).then(success).catch(fail);
}

function mypage(userId, success, fail) {
  local.get(`${url}/${userId}`).then(success).catch(fail);
}

function modifyUser(param, success, fail) {
  local.put(`${url}/modify`, JSON.stringify(param)).then(success).catch(fail);
}

function deleteUser(userId, success, fail) {
  local.delete(`${url}/${userId}`).then(success).catch(fail);
}

// function refreshUser(param, success, fail) {
//   local.post(`${url}/refresh`, JSON.stringify(param)).then(success).catch(fail);
// }

export {
  userConfirm,
  findById,
  tokenRegeneration,
  login,
  logout,
  join,
  mypage,
  modifyUser,
  deleteUser,
  // refreshUser,
};
