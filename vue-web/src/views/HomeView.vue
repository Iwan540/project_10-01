<template>
  <el-container style="height: 100%; background-color: white;">
    <el-aside width="250px" height="100vh" style="background-color: rgb(249, 249, 249); border: 1px solid #eee;">
		<div style="width: 100%; background-color: white; text-align: center;">
      <span>
			<img src="../assets/pictures/university_log.png" />
      </span>
		</div>
		<el-menu :default-openeds="[]" router>
      <el-menu-item index="/homeview/dashboard" style="font-weight: bolder;">
			<i class="el-icon-odometer" style="margin-right: 25px; font-size: 30px;"></i>
			<span slot="title" style="color: #606266;">Dashboard</span>
      </el-menu-item>
      <el-menu-item index="/homeview/email" style="font-weight: bolder;">
			<i class="el-icon-message" style="margin-right: 25px; font-size: 30px;"></i>
			<span slot="title" style="color: #606266">Email</span>
      </el-menu-item>
      <el-menu-item index="/homeview/application" style="font-weight: bolder;">
			<i class="el-icon-s-flag" style="margin-right: 25px; font-size: 30px;"></i>
			<span slot="title" style="color: #606266">Application</span>
      </el-menu-item>
      <el-menu-item index="/homeview/notice" style="font-weight: bolder;">
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

.el-container, .el-aside {
  height: 100vh; /* 100vh 表示视口的100%高度 */
}

</style>

<script>
export default {
  name: 'HomeView',
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
    } else {
      this.currentSname =  "Welcom, " + user.sname + "(Marker Coordinator)"
    }
	}
  };
</script>
