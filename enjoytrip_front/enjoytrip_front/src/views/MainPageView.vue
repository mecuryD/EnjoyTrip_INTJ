<script setup>
const imageItems = [
    {src: '1.jpg'},
    {src: '2.jpg'},
    {src: '3.jpg'},
    {src: '4.jpg'},
    {src: '5.jpg'},
    {src: '6.jpg'},
    {src: '7.jpg'},
  ]
</script>

<template>
  <div>
    <v-carousel
      cycle
      hide-delimiter-background
      delimiter-icon="stop"
      class="parent-div">
      <v-carousel-item
        class="carousel-item"
        v-for="(item,i) in imageItems"
        :key="i"
        :src="require(`@/assets/img/main/${item.src}`)"
        cover
      >
      </v-carousel-item>
    </v-carousel>
    <h1 class="font-weight-bold mb-2 display-2 children-div">
      여행을떠나요!<br/>즐거운마음으로....
    </h1>
  </div>
</template>

<style scoped>
.parent-div{
  margin-top: 0;
  padding-top: 0;
  height: 100%;
}
.carousel-item{
  filter: blur(10px);
  -webkit-filter: blur(10px);
  height: 100%;
}
.children-div{
  position: absolute;
  display: table-cell;
  color: white;
  left: 12%;
  top: 20%;
  font-family: 'EASTARJET-Medium';
  font-size: 50px;
}
::v-deep .v-btn {
  color: transparent;
  background-color: transparent;
}
::v-deep .v-icon {
  color: whitesmoke;
}

</style>