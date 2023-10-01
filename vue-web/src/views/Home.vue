<template>
  <el-container style="height: 100vh; border: 1px solid #eee;">
    <el-aside width="250px" style="background-color: rgb(249, 249, 249)">
      <div style="width: 100%; background-color: white; text-align: center;">
        <span>
          <img src="../assets/pictures/university_log.png" />
        </span>
      </div>
      <el-menu :default-openeds="[]" router>
        <el-menu-item index="/dashboard" style="font-weight: bolder;">
          <i class="el-icon-odometer" style="margin-right: 25px; font-size: 30px;"></i>
          <span slot="title" style="color: #606266;">Dashboard</span>
        </el-menu-item>
        <el-menu-item index="/cart" style="font-weight: bolder;">
          <i class="el-icon-shopping-cart-2" style="margin-right: 25px; font-size: 30px;"></i>
          <span slot="title" style="color: #606266">Cart</span>
        </el-menu-item>
        <el-menu-item index="/application" style="font-weight: bolder;">
          <i class="el-icon-s-flag" style="margin-right: 25px; font-size: 30px;"></i>
          <span slot="title" style="color: #606266">Application</span>
        </el-menu-item>
        <el-menu-item index="/notice" style="font-weight: bolder;">
          <i class="el-icon-message-solid" style="margin-right: 25px; font-size: 30px;"></i>
          <span slot="title" style="color: #606266">Notice</span>
        </el-menu-item>
        <el-menu-item index="/login" style="font-weight: bolder;">
          <i class="el-icon-s-promotion" style="margin-right: 25px; font-size: 30px;"></i>
          <span slot="title" style="color: #606266">Exit</span>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <el-container>
      <el-header style="text-align: left; font-size: 25px; height: 80px; background-color: white;">
        <i class="el-icon-coffee" style="margin-right: 15px; font-size: 25px;"></i>
        <span style="line-height: 80px;">{{ currentSname }}</span>
      </el-header>

      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<style>
.el-header {
  background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
}

.el-aside {
  color: #333;
}

.el-menu-item:hover {
  color: blueviolet;
}
</style>

<script>
export default {
  name: 'Home',
  components: {

  },
  data() {
    return {
      currentSname: "Loading"
    }
  },
  computed: {

  },
  created() {
    let user = JSON.parse(sessionStorage.getItem("user"))
    this.currentSname = "Welcom, " + user.sname
    if(user.priority == 5) {
		this.currentSname =  "Welcom, " + user.sname + "(Student)"
	  } else if(user.priority == 3) {
		this.currentSname =  "Welcom, " + user.sname + "(Course Coordinator)"
	  } else if(user.priority == 1){
		this.currentSname =  "Welcom, " + user.sname + "(Marker Coordinator)"
	  } else {
      this.currentSname =  "Welcom, " + user.sname + "(Marker)"
    }
  },
};
</script>
