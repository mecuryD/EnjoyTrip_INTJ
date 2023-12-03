<script setup>
import { ref, watch, onMounted, defineProps } from "vue";

const props = defineProps({ list: Array });
var map;
const positions = ref([]);
const markers = ref([]);
const key = process.env.VUE_APP_KAKAO_MAP_SERVICE_KEY;

onMounted(() => {
    if (window.kakao && window.kakao.maps) {
        initMap();
    } else {
        const script = document.createElement("script");
        script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${
            key}&libraries=services,clusterer`;
        /* global kakao */
        script.onload = () => kakao.maps.load(() => initMap());
        document.head.appendChild(script);
    }
});

const initMap = () => {
  const container = document.getElementById("map");
  const options = {
    center: new kakao.maps.LatLng(33.450701, 126.570667),
    level: 3,
  };
  map = new kakao.maps.Map(container, options);
  // 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
  var zoomControl = new kakao.maps.ZoomControl();
  map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
  // loadMarkers();
};

watch(
    () => props.list.values,
  () => { 
      console.log(props.list);
      positions.value = [];
      props.list.forEach((tplace) => {
          let obj = {};
          obj.latlng = new kakao.maps.LatLng(tplace.latitude, tplace.longitude);
          obj.title = tplace.title;
          obj.addr1 = tplace.addr1;
          obj.tel = tplace.tel;
          obj.firstImage = tplace.firstImage;

          positions.value.push(obj);
      });
      loadMarkers();
    },
    { deep: true }
)

const loadMarkers = () => {
  console.log(positions.value);
    // 현재 표시되어있는 marker들이 있다면 map에 등록된 marker를 제거한다.
    deleteMarkers();
    markers.value = [];
    console.log("positions :", positions);
    console.log("positions.value :", positions.value);
    positions.value.forEach((position) => {
        console.log("position : ", position);
        const marker = new kakao.maps.Marker({
            map: map, // 마커를 표시할 지도
            position: position.latlng, // 마커를 표시할 위치
            title: position.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됨.
            clickable: true, // // 마커를 클릭했을 때 지도의 클릭 이벤트가 발생하지 않도록 설정합니다
        });
        markers.value.push(marker);

        // 인포윈도우
        //////////////////////
        kakao.maps.event.addListener(marker, "click", function () {
        // 마커 위에 인포윈도우를 표시합니다
        console.log("클릭됨");
        console.log(position);
        // 커스텀 오버레이에 표시할 컨텐츠 입니다
        // 커스텀 오버레이는 아래와 같이 사용자가 자유롭게 컨텐츠를 구성하고 이벤트를 제어할 수 있기 때문에
        // 별도의 이벤트 메소드를 제공하지 않습니다
        // 마커 위에 커스텀오버레이를 표시합니다
        // 마커를 중심으로 커스텀 오버레이를 표시하기위해 CSS를 이용해 위치를 설정했습니다

        // function closeOverlay() {
        //   console.log("ㅎㅇ");
        //   // overlay.setMap(null);
        // }
        // 새로운 div 요소 생성
        const christmasOverlay = document.createElement("div");
        christmasOverlay.classList.add("christmasOverlay");

        // title 섹션 생성
        const titleDiv = document.createElement("div");
        titleDiv.classList.add("title");
        titleDiv.innerHTML = `${position.title}`;

        // close 버튼 생성
        const closeSpan = document.createElement("span");
        closeSpan.classList.add("close");
        closeSpan.setAttribute("title", "닫기");
        closeSpan.textContent = "×";
        closeSpan.addEventListener("click", closeOverlay);

        // titleDiv에 closeSpan을 추가
        titleDiv.appendChild(closeSpan);

        // body 섹션 생성
        const bodyDiv = document.createElement("div");
        bodyDiv.classList.add("body");

        // img 섹션 생성
        const imgDiv = document.createElement("div");
        imgDiv.classList.add("img");
        const img = document.createElement("img");
        const imgUrl = `${position.firstImage}`;
        img.setAttribute("src", imgUrl);
        img.setAttribute("width", "60");
        img.setAttribute("height", "40");
        imgDiv.appendChild(img);

        // desc 섹션 생성
        const descDiv = document.createElement("div");
        descDiv.classList.add("desc");
        const jibunDiv = document.createElement("div");
        jibunDiv.classList.add("jibun", "ellipsis");
        jibunDiv.textContent = `${position.addr1}`;

        // tel 정보를 포함하는 요소 생성
        const telDiv = document.createElement("div");
        telDiv.classList.add("jibun", "ellipsis");
        telDiv.textContent = `${position.tel}`;

        // descDiv에 addr1과 tel을 추가
        descDiv.appendChild(jibunDiv);
        descDiv.appendChild(telDiv);

        // bodyDiv에 imgDiv와 descDiv를 추가
        bodyDiv.appendChild(imgDiv);
        bodyDiv.appendChild(descDiv);

        // christmasOverlay에 titleDiv와 bodyDiv를 추가
        christmasOverlay.appendChild(titleDiv);
        christmasOverlay.appendChild(bodyDiv);

        // 만든 요소들을 사용하거나 삽입할 DOM에 추가하면 됩니다.
        // 예를 들어, body에 추가하는 방법:
        document.body.appendChild(christmasOverlay);

        // 닫기 함수 정의
        function closeOverlay() {
            christmasOverlay.remove(); // 요소 삭제
        }
        var overlay = new kakao.maps.CustomOverlay({
            content: christmasOverlay,
            map: map,
            position: marker.getPosition(),
        });
        });
    });
    

    // 4. 지도를 이동시켜주기
    // 배열.reduce( (누적값, 현재값, 인덱스, 요소)=>{ return 결과값}, 초기값);
    const bounds = positions.value.reduce(
        (bounds, position) => bounds.extend(position.latlng),
        new kakao.maps.LatLngBounds()
    );

    map.setBounds(bounds);
};

const deleteMarkers = () => {
  if (markers.value.length > 0) {
    markers.value.forEach((marker) => marker.setMap(null));
  }
};
</script>

<template>
    <div id="map"></div>
</template>

<style>

/* 크리스마스 테마 스타일링 */
.christmasOverlay {
  background-color: #000000;
  padding: 10px;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  font-family: Arial, sans-serif;
  color: #ececec;
}

.christmasOverlay .title {
  font-size: 20px;
  font-weight: bold;
  text-align: center;
  margin-bottom: 10px;
}

.christmasOverlay .close {
  float: right;
  cursor: pointer;
  font-size: 24px;
}

.christmasOverlay .body {
  display: flex;
  align-items: center;
}

.christmasOverlay .img {
  margin-right: 10px;
}

.christmasOverlay .desc {
  flex-grow: 1;
}

.christmasOverlay .jibun {
  font-size: 14px;
  color: #ececec;
}

.christmasOverlay .ellipsis {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
</style>