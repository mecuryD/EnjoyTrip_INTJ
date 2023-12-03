<script setup>
import { ref } from "vue";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";
import { useRouter } from "vue-router";

const router = useRouter();
const memberStore = useMemberStore();

const { isLogin } = storeToRefs(memberStore);
const { userLogin, getUserInfo } = memberStore;

const loginInfo = ref({
  userId : "",
  userPwd : ""
})

const idRules = [
  v => !!v || 'ID를 입력하세요',
  v => v.length <= 10 || 'ID는 4자 이상 10자 이하로 입력하세요',
  v => v.length >= 4 || 'ID는 4자 이상 10자 이하로 입력하세요'
]
const pwdRules = [
  v => !!v || '비밀번호를 입력하세요',
  v => v.length <= 14 || '비밀번호는 4자 이상 14자 이하로 입력하세요',
  v => v.length >= 4 || '비밀번호는 4자 이상 14자 이하로 입력하세요'
]

const reset = (() => {
  loginInfo.value.userId = "";
  loginInfo.value.userPwd = "";
  console.log("logined : ", isLogin.value);
})

const doLogin = async () => {
  await userLogin(loginInfo.value);
  let token = sessionStorage.getItem("accessToken");
  console.log("111. ", token);
  if (isLogin) {
    console.log("로그인 성공");
    getUserInfo(token);
  }
  console.log("isLogin: ", isLogin);
  // router.go(0);
  router.push("/");
}
</script>

<template>
  <div>
    <v-card class="joincard">
      <v-card-title primary-title>
        <div>
          <h3 class="headline mb-0">로그인</h3>
          <div> {{ card_text }} </div>
        </div>
      </v-card-title>

      <v-card class="inputs" flat>
        <v-text-field
          class="input-box"
          v-model="loginInfo.userId"
          :rules="idRules"
          :counter="10"
          label="ID"
          required
        ></v-text-field>
        <v-text-field
          class="input-box"
          v-model="loginInfo.userPwd"
          type="password"
          :rules="pwdRules"
          :counter="14"
          label="Password"
          required
        ></v-text-field>
        
      </v-card>

      <v-card-actions class="actions">
        <v-spacer></v-spacer>
        <v-btn flat color="orange" @click="doLogin">로그인</v-btn>
        <v-btn flat color="orange" @click="reset">초기화</v-btn>
        <v-spacer></v-spacer>
      </v-card-actions>
    </v-card>
  </div>
</template>

<style scoped>
.joincard{
  width: 60%;
  margin: auto;
  text-align: center;
}
.inputs{
  margin: 10%;
  padding-left: 15%;
  padding-right: 15%;
  text-align: center;
  display: flex;
  flex-direction: column;
}
.input-box{
  margin-bottom: 2%;
  text-align: center;
}

.actions{
  text-align: center;
  margin-left: auto;
  margin-right: auto;
  margin-bottom: 5%;
}
</style>