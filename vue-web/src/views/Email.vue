<template>
    <div>
        <h4 style="color: rgba(0,70,127);">Email</h4>
      <!-- 下拉框控件 -->
      <!-- 外层容器，使用flex布局 -->
      <div style="display: flex; align-items: center; gap: 10px;">

        <!-- 下拉选择框 -->
        <el-select v-model="selectedFilter" placeholder="select" @change="onChange">
          <el-option label="Email" value="email"></el-option>
          <el-option label="Name" value="sname"></el-option>
        </el-select>

        <!-- 当选择Email时显示的部分 -->
        <div v-if="selectedFilter === 'email'">
          <el-input size="medium" placeholder="Input email" style="width: 300px; margin: 10px 5px;" suffix-icon="el-icon-search" v-model="email"></el-input>
          <el-button size="medium" type="primary" @click="filter()">Filter</el-button>
          <el-button size="medium" type="warning" @click="reset">Reset</el-button>
        </div>

        <!-- 当选择Name时显示的部分 -->
        <div v-if="selectedFilter === 'sname'">
          <el-input size="medium" placeholder="Input name" style="width: 300px; margin: 10px 5px;" suffix-icon="el-icon-search" v-model="sname"></el-input>
          <el-button size="medium" type="primary" @click="filter2()">Filter</el-button>
          <el-button size="medium" type="warning" @click="reset">Reset</el-button>
        </div>

      </div>






      <!-- 表格控件 -->
        <el-table :data="tableData" style="margin-top: 20px;">
            <el-table-column prop="auid" label="Auid" width="120" sortable>
            </el-table-column>
            <el-table-column prop="upi" label="UPI" width="100" sortable>
            </el-table-column>
            <el-table-column prop="sname" label="Name" width="200" sortable>
            </el-table-column>
            <el-table-column prop="email" label="Email" width="320" sortable>
                <template slot-scope="scope">
                    <el-link @click="sendEmail(scope.row)"><i class="el-icon-message"></i> {{ scope.row.email }}</el-link>
                </template>
            </el-table-column>
          <el-table-column prop="priority" label="Role" sortable>
            <template slot-scope="scope">
              <el-tag :style="{ backgroundColor: 'rgba(202,11,255,0.82)', color: 'white' }" v-if="scope.row.priority==5">Student</el-tag>
              <el-tag :style="{ backgroundColor: '#409eff', color: 'white' }" v-if="scope.row.priority==3">Course Coordinator</el-tag>
              <el-tag :style="{ backgroundColor: 'rgba(255,74,78,0.78)', color: 'white' }" v-if="scope.row.priority==1">Marker Coordinator</el-tag>
              <el-tag :style="{ backgroundColor: '#49b749', color: 'white' }" v-if="scope.row.priority==6">Marker</el-tag>
            </template>
          </el-table-column>


          <el-table-column prop="priority" label="Priority Setting"  v-if="user.priority==1" sortable>
                <template slot-scope="scope">
                    <el-select v-model="scope.row.priority" placeholder="select priority" @change="selectChange(scope.row)" v-if="scope.row.priority==5 || scope.row.priority==6">
                        <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
                        </el-option>
                    </el-select>
                </template>
            </el-table-column>
        </el-table>

    </div>
</template>
<script>

import request from '@/utils/request';
export default {
    name: 'Email',
    data() {
        return {
            tableData: [],
            selectedFilter: 'email', // 初始值为空
            user: {},
            email: '',
            sname: '',
            options: [
                {
                    value: 5,
                    label: 'student'
                },
                {
                    value: 6,
                    label: 'marker'
                }
            ]
        }
    },
    methods: {
        load() {
            this.user = JSON.parse(sessionStorage.getItem('user'))
            request.get("/sysuser", {
                params: {

                }
            }).then(res => {
                this.tableData = res.data
            })
        },
      filter() {
        request.get("/sysuser/search", {
          params: {
            email: this.email
          }
        }).then(res => {
          this.tableData = res.data;
        }).catch(error => {
          console.error("Error fetching data:", error);
          this.$message.error('Error fetching users by email.');
        });
      },

      filter2() {
        request.get("/sysuser/search2", {
          params: {
            sname: this.sname
          }
        }).then(res => {
          this.tableData = res.data;
        }).catch(error => {
          console.error("Error fetching data:", error);
          this.$message.error('Error fetching users by name.');
        });
      },

      reset() {
          this.sname = ""
        this.email = ""
          this.load()
        },
        sendEmail(row) {
            const email = row.email;
            const subject = encodeURIComponent('Hello');
            const body = encodeURIComponent('Hi there!');
            window.location.href = `mailto:${email}?subject=${subject}&body=${body}`;
        },
        selectChange(row) {
            request.put("/sysuser/update", row).then(res=>{
                this.load()
            })
        }
    },
    created() {
        this.load()
    }
}
</script>
