import { localAxios } from "@/util/http-commons";

const local = localAxios();   //axios instance 호출

const url = "/plan"

function writePlan(plan, success, fail) {
  console.log(JSON.stringify(plan));
  local.post(`${url}`, JSON.stringify(plan)).then(success).catch(fail);
}

function detailPlan(planId, success, fail) {
  local.get(`${url}/${planId}`).then(success).catch(fail);
}

function listPlan(success, fail) {
  local.get(`${url}/all`).then(success).catch(fail);
}

function myListPlan(userId, success, fail) {
  local.get(`${url}/all/${userId}`).then(success).catch(fail);
}

function modifyPlan(Plan, success, fail) {
  local.put(`${url}/modify`, JSON.stringify(Plan)).then(success).catch(fail);
}

function deletePlan(planId, success, fail) {
  local.delete(`${url}/${planId}`).then(success).catch(fail);
}

export {
  writePlan,
  detailPlan,
  listPlan,
  myListPlan,
  modifyPlan,
  deletePlan,
};
