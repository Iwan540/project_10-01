<template>
  <div id="login" style="width: 100%; height: 100vh;">
    <div id="bg1"
         style="width: 500px; border: #ccc solid 1px; display: flex; margin: 200px auto; height: 400px; border-radius: 20px; margin-left: 60%;">
      <div id=""
           style="width: 100%; border: #ccc solid 1px; padding: 50px 20px 20px 20px; background-color: rgba(131,171,212, 0.5); border-radius: 10px; border-bottom-right-radius: 20px;">
        <h1 style="text-align: center; margin-bottom: 25px; font-size: 25px;">Welcom Login</h1>
        <!-- 表单 -->
        <el-form ref="form" :model="form" label-width="80px" :rules="rules" size="medium" autofocus="false">
          <el-form-item label="Email" prop="email">
            <el-input v-model="form.email" style="width: 80%;" clearable></el-input>
          </el-form-item>
          <el-form-item label="Password" prop="password">
            <el-input v-model="form.passwd" style="width: 80%;" type="password" show-password></el-input>
          </el-form-item>
          <el-form-item style="height: 20px;">
            <p style="display: inline-block; margin-right: 10px; font-size: 10px;">Remember me</p>
            <el-checkbox v-model="checked"></el-checkbox>
          </el-form-item>
          <el-form-item>
            <div style="display: flex; justify-content: space-between; width:80%" >
              <el-button type="primary" @click="onSubmit" style="width: 48%;">Login</el-button>
              <el-button type="info" @click="openRegisterForm" style="width: 48%; background-color: #42b983; border-color: #42b983;">Register</el-button>
            </div>
          </el-form-item>
          <!--   注册表单         -->

          <el-dialog title="Register" :visible.sync="registerFormVisible" width="400px" :modal="false">



            <el-form :model="registerForm" label-position="top" :rules="registerFormRules" ref="registerFormRef">


              <el-form-item label="UPI" prop="upi">
                <el-input v-model="registerForm.upi" placeholder="Enter UPI" clearable></el-input>
              </el-form-item>

              <el-form-item label="Name" prop="sname">
                <el-input v-model="registerForm.sname" placeholder="Enter Name" clearable></el-input>
              </el-form-item>

              <el-form-item label="Email">
                <el-input v-model="registerForm.email" placeholder="Enter Email"></el-input>
              </el-form-item>

              <el-form-item label="Password">
                <el-input v-model="registerForm.passwd" type="password" placeholder="Enter Password"></el-input>
              </el-form-item>

              <el-form-item>
                <el-button type="primary" @click="handleRegister" class="register-btn" >Register</el-button>

              </el-form-item>
            </el-form>
          </el-dialog>


        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import request from '../utils/request.js'
export default {
  name: 'Login',
  data() {
    return {
      registerFormRules: {
        upi: [
          { required: true, message: 'UPI is required', trigger: 'blur' }
        ],
        sname: [
          { required: true, message: 'Name is required', trigger: 'blur' }
        ]
      },
      form: {},
      checked: false,
      rules: {},
      registerFormVisible: false,  // 用于控制注册表单的可见性
      registerForm: {  // 用于绑定注册表单数据
        email: '',
        passwd: '',
        upi: '',
        sname: ''
      }

    }
  },

  methods: {
    openRegisterForm() {
      this.registerFormVisible = true;
    },
    handleRegister() {
      request.post("/sysuser/register", this.registerForm).then(res => {
        if (res.code === '0') {
          this.$message.success("Registered successfully");
          this.registerFormVisible = false;

          // 自动登录
          request.post("/sysuser/login", this.registerForm).then(loginRes => {
            if (loginRes.code == '0') {
              this.$message.success("success login");
              sessionStorage.setItem("user", JSON.stringify(loginRes.data));
              if (loginRes.data.priority >= 5) {
                this.$router.push('/dashboard');
              } else {
                this.$router.push('/homeview/dashboard');
              }
            } else {
              this.$message.error(loginRes.msg);
            }
          });

        } else {
          this.$message.error(res.msg);
        }
      });
    },


    onSubmit() {
      console.log(this.form)
      request.post("/sysuser/login", this.form).then(res => {
        console.log(res);
        console.log(this.form)
        if (res.code == '0') {
          this.$message.success("success login");
          sessionStorage.setItem("user", JSON.stringify(res.data))
          if (res.data.priority >= 5) {
            this.$router.push('/dashboard')
          } else {
            this.$router.push('/homeview/dashboard')
          }
        } else {
          this.$message.error(res.msg);
          this.form = {};
        }
      })
    }
  }
}
</script>

<style>
#login {
  background: url(../assets/pictures/bg.jpg);
  opacity: 0.9;
  background-position: center;
  background-repeat: no-repeat;
  background-size: 100% 100%;
  position: fixed;


}

#bg1 {
  box-shadow: 10px 10px 10px rgba(0, 0, 0, .5);


  -moz-box-shadow: 10px 10px 10px rgba(0, 0, 0, .5);

  -webkit-box-shadow: 10px 10px 10px rgba(0, 0, 0, .5);
}

#bg2 {
  width: 50%;
  opacity: 0.8;
  background-size: 100% 100%;
  border-top-left-radius: 20px;
  border-bottom-left-radius: 20px;


}

.register-btn {
  width: 100%;
  background-color: #42b983;
  border-color: #42b983;
  transition: background-color 0.3s;
}

.register-btn:hover {
  background-color: #35a876;
}





</style>
