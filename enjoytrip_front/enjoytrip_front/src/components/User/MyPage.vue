<script setup>
import { ref, watch, onMounted } from "vue";
import { useRouter } from "vue-router";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";
import { mypage, modifyUser } from "@/api/user";

const memberStore = useMemberStore();

const { userInfo } = storeToRefs(memberStore);
console.log(userInfo);

const router = useRouter();

const newUserInfo = ref({});
const userPwdComfirm = ref("");

const emailDomainItems = [
  'naver.com', 'gmail.com', 'hanmail.net', 'ssafy.com', 'data.go.kr'
]
const genderComboItems = [
  // { name : '남성', value : 1 },
  // { name : '여성', value : 2 },
  '남성', '여성'
]
const genderValue = ref("");
watch(genderValue, (newValue, oldValue) => {
  if (newValue === genderComboItems[0]) { 
    newUserInfo.value.gender = 1;
    console.log(oldValue + "에서 " + newValue + "으로 변경!")
  }else {
    newUserInfo.value.gender = 2;
  }
  console.log(newUserInfo.value.gender)
})

const birthdate = ref(null);
watch(birthdate, val => {
  let date = birthdate.value;
  newUserInfo.value.birth = (new Date(date - (date).getTimezoneOffset() * 60000)).toISOString().substr(0, 10)
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
const pwdRules2 = [
  v => v === newUserInfo.value.userPwd || '비밀번호를 다시 확인하세요'
]

const cancel = (() => {
  router.push({ name: "main" });
})

onMounted(() => {
  console.log("newUserInfo : ", newUserInfo);
  mypage(userInfo.value.userId, (data) => {
    console.log(data.data);
    newUserInfo.value = data.data;
    genderValue.value = genderComboItems[newUserInfo.value.gender - 1];
  }, (error) => {
    console.log("에러~~~~~~~~~~~~~~~~~~~~~~~~");
    console.log(error);
  });
})

const doModifyUser = () => {
  console.log("newUser",newUserInfo.value);
  modifyUser(newUserInfo.value, () => {
    router.push({ name: "main" });
  }, (error) => {
    console.log(error);
    router.push({ name: "errorpg" });
  });
};
</script>

<template>
  <div>
    <v-card class="joincard">
      <v-card-title primary-title>
        <div>
          <h3 class="headline mb-0">마이페이지</h3>
          <div> {{ card_text }} </div>
        </div>
      </v-card-title>

      <v-card class="inputs" flat>
        <v-text-field
          class="input-box"
          v-model="newUserInfo.userId"
          :rules="idRules"
          :counter="10"
          label="ID"
          required
          readonly
        ></v-text-field>

        <v-text-field
          class="input-box"
          v-model="newUserInfo.userPwd"
          type="password"
          :rules="pwdRules"
          :counter="14"
          label="Password"
          required
        ></v-text-field>

        <v-text-field
          class="input-box"
          v-model="userPwdComfirm"
          type="password"
          :rules="pwdRules2"
          :counter="14"
          label="Password 확인"
          required
        ></v-text-field>

        <v-text-field
          class="input-box"
          v-model="newUserInfo.userName"
          :rules="nameRules"
          label="이름"
          required
        ></v-text-field>

        <!-- 성별 -->
        <v-select
          class="input-box"
          label="성별"
          v-model="genderValue"
          :items="genderComboItems"
          item-title="name"
          item-value="value"
        ></v-select>
        
        <!-- 이메일 -->
        <div class="email-parent">
          <v-text-field
            class="input-box email-box"
            v-model="newUserInfo.emailId"
            :rules="idRules"
            label="이메일 id"
            required
          ></v-text-field>
          <h2 style="margin-top: 3%;">@</h2>
          <v-select
          class="input-box email-box"
          label="domain"
          v-model="newUserInfo.emailDomain"
          :items="emailDomainItems"
        ></v-select>
        </div>

        <!-- 생년월일 -->
        <v-dialog width="500">
          <template v-slot:activator="{ props }">
            <v-text-field
            @click="toggleCalendar"
            class="btn-cal input-box"
            label="생년월일"
            prepend-inner-icon="mdi-calendar"
            v-model="newUserInfo.birth"
            v-bind="props">
          </v-text-field>
          </template>
          <template v-slot:default="{isActive}">
            <v-card class="input-box" title="생년월일">
              <v-date-picker
                class="datepicker"
                title=""
                scrollable="true"
                v-model="birthdate"
                >
              </v-date-picker>
              <v-btn text="선택" @click="isActive.value = false"
              ></v-btn>
            </v-card>
          </template>
        </v-dialog>

      </v-card>

      <v-card-actions class="actions">
        <v-spacer></v-spacer>
        <v-btn flat color="orange" @click="doModifyUser">회원정보 수정</v-btn>
        <v-btn flat color="orange" @click="cancel">취소</v-btn>
        <v-spacer></v-spacer>
      </v-card-actions>
    </v-card>
  </div>
</template>

<style scoped>
.joincard{
  width: 60%;
  margin-left: auto;
  margin-right: auto;
  margin-top: 10%;
  margin-bottom: 10%;
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
.email-box{
  width: 40%;
}
.email-parent{
  display: flex;
  flex-direction: row;
}
.btn-cal{
  border: 1px;
}
.datepicker{
  margin-left: auto;
  margin-right: auto;
}
.actions{
  text-align: center;
  margin-left: auto;
  margin-right: auto;
  margin-bottom: 5%;
}
</style>