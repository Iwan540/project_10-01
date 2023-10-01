<template>
    <div>
        <h4 style="color: rgba(0,70,127);">Application</h4>
      <el-input  size="medium" placeholder="Input Course Name" style="width: 300px; margin: 10px 5px;"  suffix-icon="el-icon-search" v-model="selectCname"></el-input>
      <el-button size="medium" type="primary" @click="load()">Filter</el-button>
      <el-button size="medium" type="warning" @click="reset">Reset</el-button>
        <!-- 表格控件 -->
        <el-table :data="tableData" style="margin-top: 20px;">
            <el-table-column prop="cname" label="Course" width="150" sortable>
            </el-table-column>
            <el-table-column prop="term" label="Term" width="140" sortable>
            </el-table-column>
          <!-- 2023-9-24 -->

          <el-table-column prop="status" label="Status" width="200" sortable>
            <template slot-scope="scope">
              <el-tag v-if="scope.row.status === 'Under consideration'" :style="{ backgroundColor: '#409eff', color: 'white' }">Under consideration</el-tag>
              <el-tag type="danger" v-if="scope.row.status === 'Rejected'">Rejected</el-tag>
              <el-tag type="success" v-if="scope.row.status === 'Accepted'">Accepted</el-tag>
            </template>
          </el-table-column>

          <el-table-column prop="rate" label="Rates" width="150" sortable>
            <template slot-scope="scope">
              <el-tag v-if="!scope.row.rate || scope.row.rate === 'Unrated'" :style="{ backgroundColor: '#409eff', color: 'white' }">Unrated</el-tag>

              <el-tag type="success" v-if="scope.row.rate==='Qualified'">Qualified</el-tag>
              <el-tag type="danger" v-if="scope.row.rate==='Not qualified'">Not qualified</el-tag>
            </template>
          </el-table-column>









          <el-table-column prop="details" label="Application Detail"  width="200" >
                <template slot-scope="scope">
                    <el-link @click="showDetails(scope.row)">Details<i class="el-icon-view el-icon--right"></i></el-link>
                </template>
            </el-table-column>
          <el-table-column label="Estimate Working Hours" width="250" align="center" sortable prop="minGrade" >
            <template slot-scope="scope">
              <span>{{ scope.row.status === 'Accepted' ? scope.row.minGrade : 0 }}</span>
            </template>
          </el-table-column>



        </el-table>

      <PaginationTest
          :current-page="currentPage"
          :page-size="pageSize"
          :total="total"
          @change-page="handleCurrentChange"
          @change-size="handleSizeChange">
      </PaginationTest>


      <!-- 隐藏的apply表单 -->
        <el-dialog title="Application" :visible.sync="ApplyFormVisible">
            <el-form ref="form" :model="application">
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="CV" style="width: 60%"><br>
                            <el-button type="primary" plain @click="goToLink()">Download CV</el-button>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="Academic Record" style="width: 60%">
                            <el-button type="primary" plain @click="goToLink1()">Download Academic Record"</el-button>
                        </el-form-item>
                    </el-col>
                </el-row>
                <h4 style="margin-bottom: 10px;">Personal Information</h4>
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="Full name" style="width: 100%;">
                            <el-input v-model="user.sname" disabled></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="UPI" style="width: 100%;">
                            <el-input v-model="user.upi" disabled></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="AUID" style="width: 100%;">
                            <el-input v-model="user.auid" disabled></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="Preferred email" style="width: 100%;">
                            <el-input v-model="user.email" disabled></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="12">
                      <el-form-item label="Enrolment details for the semester" style="width: 100%;">
                        <el-input v-model="application.enrolmentDetailsSemester" disabled ></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="Maximum working hours per week" style="width: 100%;">
                            <el-input v-model="application.workingHours" disabled></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-form-item style="margin-top: 20px; " label="Are you currently overseas?">
                        <el-radio v-model="application.overseas" :label="true" disabled>Yes</el-radio>
                        <el-radio v-model="application.overseas" :label="false" disabled>No</el-radio>
                    </el-form-item>
                </el-row>
                <el-row>
                    <el-form-item label="Will you back in NZ? " v-if="application.overseas">
                        <el-radio v-model="application.willback" :label="true" disabled>Yes</el-radio>
                        <el-radio v-model="application.willback" :label="false" disabled>No</el-radio>
                    </el-form-item>
                </el-row>
                <el-row>
                    <el-form-item label="Are you citizen or permanent resident?">
                        <el-radio v-model="application.permanent" :label="true" disabled>Yes</el-radio>
                        <el-radio v-model="application.permanent" :label="false" disabled>No</el-radio>
                    </el-form-item>
                </el-row>
                <el-row>
                    <el-form-item label="Do you have a valid work visa?" v-if="!application.permanent">
                        <el-radio v-model="application.visa" :label="true" disabled>Yes</el-radio>
                        <el-radio v-model="application.visa" :label="false" disabled>No</el-radio>
                    </el-form-item>
                </el-row>
                <el-row>
                    <el-form-item label="Do you have any other TA/GTA  contracts for that semester?">
                        <el-radio v-model="application.contracts" :label="true" disabled>Yes</el-radio>
                        <el-radio v-model="application.contracts" :label="false" disabled>No</el-radio>
                    </el-form-item>
                </el-row>
                <el-row>
                    <el-form-item label="" style="width: 50%;" v-if="application.contracts">
                      <el-input type=" textarea" v-model="application.description" placeholder="description of contracts" disabled></el-input>

<!--
                      <el-input v-model="application.description" placeholder="description of contracts"></el-input>
   -->
                    </el-form-item>
                </el-row>
                <el-row>
                    <el-form-item label="Are you Undergraduate or Postgraduate student?">
                        <el-radio v-model="application.undergraduate" label="Undergraduate" disabled>Undergraduate</el-radio>
                        <el-radio v-model="application.undergraduate" label="Postgraduate" disabled>Postgraduate</el-radio>
                        <br/>
                        <i>* Postgraduate means that student has already completed a degree.</i>
                    </el-form-item>
                </el-row>

                <h4 style="margin-bottom: 10px;">Other Information</h4>

                <el-row>
                    <el-form-item label="Have you done this course before?">
                        <el-radio v-model="application.studied" :label="true" disabled>Yes</el-radio>
                        <el-radio v-model="application.studied" :label="false" disabled>No</el-radio>
                    </el-form-item>
                </el-row>
              <el-row>
                <el-form-item label="Your grade of this course" style="width: 80%;" v-if="application.studied== 1">
                  <el-input type=" text" v-model="application.grades" disabled></el-input>

                </el-form-item>
                <el-form-item label="Your qualification details" style="width: 80%;" v-if="application.studied== 0">
                  <el-input type=" textarea" v-model="application.qualification" disabled></el-input>

                </el-form-item>
              </el-row>
                <el-row>
                    <el-form-item label="Relevant previous experience" style="width: 80%;">
                        <el-input type="textarea" v-model="application.experience" placeholder="e.g. has marked/tutored that course before or a similar course overseas" disabled></el-input>
                    </el-form-item>
                </el-row>
            </el-form>
        </el-dialog>
    </div>
</template>

<script>

import request from '@/utils/request';
import PaginationTest from '@/views/PaginationTest.vue';
export default {
    name: 'Application',
  components: {
    PaginationTest
  },
    data() {
        return {
            user: {},
            selectValue: '',
            total: 0,
            tableData: [],
            selectCname: "",
            ApplyFormVisible: false,
            application: {},
          currentPage: 1,
          pageSize: 10,
          courseName: '',
        }
    },
    methods: {
        load() {
            request.get("/apply/select", {
              params: {
                auid: this.user.auid,
                priority: this.user.priority,
                cname: this.selectCname,
                cur: this.currentPage,
                size: this.pageSize,
              }

            }).then(res => {

              console.log(res)
              console.log("Sending request with params:", {
                auid: this.user.auid,
                priority: this.user.priority,
                cname: this.selectCname,
                cur: this.currentPage,
                size: this.pageSize,
              });

              this.tableData = res.data.records;
              this.total = res.data.total
            })
        },
      handleCurrentChange(val) {
        console.log(val)
        this.currentPage = val
        this.load()
      },
      handleSizeChange(val) {
        this.pageSize = val
        this.load()
      },
        showDetails(row) {
            console.log(row)
            this.ApplyFormVisible = true
            this.application = row
        },
        goToLink() {
            window.location.href = this.application.resume
        },
        goToLink1() {
            window.location.href = this.application.tranciption
        },
        sliderChange(row) {
            request.put("/apply/addapply", row).then(res => {

                this.load()

            })
        },
      reset() {
        this.selectCname = ""
        this.load();

      }
    },
    created() {
        this.user = JSON.parse(sessionStorage.getItem('user'))
        this.load()
    },
    mounted() {


    }
}

</script>
