<template>
    <div>
        <h4 style="color: rgba(0,70,127);">Cart</h4>
      <el-input  size="medium" placeholder="Input Course Name" style="width: 300px; margin: 10px 5px;"  suffix-icon="el-icon-search" v-model="courseName"></el-input>
      <el-button size="medium" type="primary" @click="filter()">Filter</el-button>
      <el-button size="medium" type="warning" @click="reset">Reset</el-button>
        <!-- 表格控件 -->
        <el-table :data="tableData" style="margin-top: 20px;">
            <el-table-column prop="cname" label="Course" width="100">
            </el-table-column>
            <el-table-column prop="term" label="Term" width="100">
            </el-table-column>

          <!-- 2023-9-24 -->
            <el-table-column prop="minGrade" label="Available marker hours" sortable  align="center">
            </el-table-column>


          <el-table-column prop="favors" label="Preference rating" sortable>
            <template slot-scope="scope">
              <div class="block">
                <el-slider v-model="scope.row.favors" :step="5" @change="sliderChange(scope.row)">
                </el-slider>
              </div>
            </template>
          </el-table-column>

            <el-table-column prop="details" label="Details">
                <template slot-scope="scope">
                    <el-link @click="showDetails(scope.row)">Details<i class="el-icon-view el-icon--right"></i></el-link>
                </template>
            </el-table-column>
            <el-table-column prop="apply" label="Apply now">
                <template slot-scope="scope">
                    <el-button type="warning" @click="addApply(scope.row)" v-if="!scope.row.applied">Apply now</el-button>
                    <el-tag type="success" v-if="scope.row.applied">applied</el-tag>
                </template>
            </el-table-column>

        </el-table>

        <!-- 分页控件>
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage"
            :page-sizes="[5, 10, 15]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
        </el-pagination> -->

        <!-- 隐藏的Details表单 -->
        <el-dialog title="Course Details" :visible.sync="dialogFormVisible">
          <el-form :model="form" style="display: flex; flex-wrap: wrap; justify-content: space-between;">


            <!-- 第一列 -->
            <div style="flex: 1; padding-right: 10px;">
              <el-form-item label="1.Current enrolled students">
                <el-input v-model="form.selected" disabled></el-input>
              </el-form-item>
              <el-form-item label="3.Course Coordinator Name">
                <el-input v-model="form.coursecoordinatorname" disabled></el-input>
              </el-form-item>
              <el-form-item label="5.Course Director Name">
                <el-input v-model="form.coursedirectorname" disabled></el-input>
              </el-form-item>
              <el-form-item label="7.Available marker hours?">
                <el-input v-model="form.minGrade" disabled></el-input>
              </el-form-item>


            </div>

            <!-- 第二列 -->
            <div style="flex: 1; padding-left: 10px;">
              <el-form-item label="2.Estimated number of students">
                <el-input v-model="form.capacity" disabled></el-input>
              </el-form-item>
              <el-form-item label="4.Course Coordinator Email">
                <el-input v-model="form.coursecoordinatoremail" disabled></el-input>
              </el-form-item>
              <el-form-item label="6.Course Director Email">
                <el-input v-model="form.coursedirectoremail" disabled></el-input>
              </el-form-item>
              <el-form-item label="8.Needs marker?">
                <el-input v-model="form.marker" disabled></el-input>
              </el-form-item>


            </div>

            <!-- 9.Number and type of assessments (full row) -->
            <div style="flex-basis: 100%; padding: 0px 0;">
              <!-- 9 和 9.1 并列 -->
              <div style="display: flex; padding: 10px 0;">
                <!-- 9.Number and type of assessments -->
                <div style="flex: 0.50; padding-right: 10px;">
                  <el-form-item label="9.Number and type of assessments">
                    <el-input v-model="form.details" disabled></el-input>
                  </el-form-item>
                </div>

                <!-- 9.1 (新加的字段) -->
                <div style="flex: 0.50; padding-left: 10px;">
                  <el-form-item label="10.Minimum Grade Requirement">
                    <el-input v-model="form.requirement" disabled></el-input>
                  </el-form-item>
                </div>
              </div>




            </div>


            <!-- 11 and 121 (side by side) -->
            <div style="flex: 0.75; padding-right: 10px;">
              <el-form-item label="11.Convert marker to tutor hours OR Convert tutor to marker hours">
                <el-input v-model="form.courseCoordinator" disabled></el-input>
              </el-form-item>
            </div>
            <div style="flex: 0.25; padding-left: 10px;">
              <el-form-item label="12. A. Convert hours">
                <el-input v-model="form.hour" disabled></el-input>
              </el-form-item>
            </div>

            <!-- 12. Marker’s responsibility for the course (full row) -->
            <div style="flex-basis: 100%; padding: 10px 0;">
              <el-form-item label="13. Marker’s responsibility for the course">
                <el-input v-model="form.courseDirector" disabled></el-input>
              </el-form-item>
            </div>
          </el-form>

          <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">Exit</el-button>
            </div>
        </el-dialog>


        <!-- apply表单 -->
        <el-dialog title="Application" :visible.sync="ApplyFormVisible">
          <el-form :rules="rules" ref="form" :model="application">
                <el-row :gutter="20">
                    <el-col :span="12">

                      <el-form-item label="1. CV (Only support for pdf)" prop="resume">
                        <el-upload
                            class="upload-demo"
                            drag
                            action="http://localhost:8088/file/upload"
                            multiple
                            :limit="3"
                            :on-success="uploadSuccess"
                            :data="uploadData"
                            :before-upload="beforeFileUpload">
                          <i class="el-icon-upload"></i>
                          <div class="el-upload__text">Drag the file or <em>Click here</em></div>
                        </el-upload>
                      </el-form-item>



                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="12">




                      <el-form-item label="2. Academic record (Only support for pdf)" prop="tranciption">
                        <el-upload
                            class="upload-demo"
                            drag
                            action="http://localhost:8088/file/upload"
                            multiple
                            :limit="3"
                            :on-success="uploadSuccess1"
                            :data="uploadData"
                            :before-upload="beforeFileUpload">
                          <i class="el-icon-upload"></i>
                          <div class="el-upload__text">Drag the file or <em>Click here</em></div>
                        </el-upload>
                      </el-form-item>



                    </el-col>
                </el-row>
                <h4 style="margin-bottom: 10px;">Personal Information</h4>
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="3. Full name" style="width: 100%;">
                            <el-input v-model="user.sname" disabled></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="4. UPI" style="width: 100%;">
                            <el-input v-model="user.upi" disabled></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="5. AUID" style="width: 100%;">
                            <el-input v-model="user.auid" disabled></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="6. Prefered Email" style="width: 100%;">
                            <el-input v-model="user.email" disabled></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="12">
                      <el-form-item label="7. Enrolment details for the semester" style="width: 100%;" prop="enrolmentDetailsSemester">
                        <el-input v-model="application.enrolmentDetailsSemester" placeholder="degree / year - e.g. 2ⁿᵈ year BSc, 1ˢᵗ year PhD"></el-input>
                      </el-form-item>

                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="8. Maximum working hours per week" style="width: 100%;" prop="workingHours">


                          <el-input type="number" v-model.number="application.workingHours" :min="1" placeholder="Please enter an integer number"></el-input>

                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-form-item style="margin-top: 20px; " label="9. Are you currently overseas?" prop="overseas">
                        <el-radio v-model="application.overseas" :label="1">Yes</el-radio>
                        <el-radio v-model="application.overseas" :label="0">No</el-radio>
                    </el-form-item>
                </el-row>
                <el-row>
                    <el-form-item label="9.1 Will you back in NZ? " v-if="application.overseas" prop="willback">
                        <el-radio v-model="application.willback" :label="1">Yes</el-radio>
                        <el-radio v-model="application.willback" :label="0">No</el-radio>
                    </el-form-item>
                </el-row>
                <el-row>
                    <el-form-item label="10. Are you citizen or permanent resident?" prop="permanent">
                        <el-radio v-model="application.permanent" :label="1">Yes</el-radio>
                        <el-radio v-model="application.permanent" :label="0">No</el-radio>
                    </el-form-item>
                </el-row>
                <el-row>
                    <el-form-item label="10.1 Do you have a valid work visa?" v-if="!application.permanent" prop="visa">
                        <el-radio v-model="application.visa" :label="1">Yes</el-radio>
                        <el-radio v-model="application.visa" :label="0">No</el-radio>
                    </el-form-item>
                </el-row>
                <el-row>
                    <el-form-item label="11. Do you have any other TA/GTA  contracts for that semester?" prop="contracts">
                        <el-radio v-model="application.contracts" :label="1">Yes</el-radio>
                        <el-radio v-model="application.contracts" :label="0">No</el-radio>
                    </el-form-item>
                </el-row>
                <el-row>
                    <el-form-item label="" style="width: 60%;" v-if="application.contracts" prop="description" >
                      <el-input type=" textarea" v-model="application.description" placeholder="description of contracts"></el-input>


                    </el-form-item>
                </el-row>
                <el-row>
                    <el-form-item label="12. Are you Undergraduate or Postgraduate student?" prop="undergraduate">
                        <el-radio v-model="application.undergraduate" label="Undergraduate">Undergraduate</el-radio>
                        <el-radio v-model="application.undergraduate" label="Postgraduate">Postgraduate</el-radio>
                        <br/>
                        <i  style="font-size: 12px;color: #409EFF;">* Postgraduate means that student has already completed a degree.</i>
                    </el-form-item>
                </el-row>

                <h4 style="margin-bottom: 10px;">Other Information</h4>

                <el-row>
                    <el-form-item label="13. Have you take this course before?" prop="studied">
                        <el-radio v-model="application.studied" :label="1">Yes</el-radio>
                        <el-radio v-model="application.studied" :label="0">No</el-radio>
                    </el-form-item>
                </el-row>
                <el-row>
                  <el-form-item label="13.1 Your grade of this course" style="width: 100%;" v-if="application.studied == 1" prop="grades">
                    <el-select v-model="application.grades" placeholder="Select your grade for this course" style="width: 80%;">
                      <el-option label="A+" value="A+"></el-option>
                      <el-option label="A" value="A"></el-option>
                      <el-option label="A-" value="A-"></el-option>
                      <el-option label="B+" value="B+"></el-option>
                      <el-option label="B" value="B"></el-option>
                      <el-option label="B-" value="B-"></el-option>
                      <el-option label="C+" value="C+"></el-option>
                      <el-option label="C" value="C"></el-option>
                      <el-option label="C-" value="C-"></el-option>
                    </el-select>
                  </el-form-item>


                  <el-form-item label="13.2 Your qualification details" style="width: 80%;" v-if="application.studied== 0" prop="qualification">
                    <el-input type=" textarea" v-model="application.qualification" placeholder=" e.g. overseas students, an explanation why you are qualified"></el-input>

                  </el-form-item>
                </el-row>
                <el-row>
                    <el-form-item label="14. Relevant previous experience" style="width: 80%;" prop="experience">
                        <el-input type="textarea" v-model="application.experience" placeholder="e.g. has marked/tutored that course before or a similar course overseas"></el-input>
                    </el-form-item>
                </el-row>
                <el-form-item >
                    <el-button type="primary" @click="submit">Submit</el-button>
                </el-form-item>

            </el-form>
        </el-dialog>
      <PaginationTest
          :current-page="currentPage"
          :page-size="pageSize"
          :total="total"
          @change-page="handleCurrentChange"
          @change-size="handleSizeChange">
      </PaginationTest>
    </div>
</template>
<script>

import request from '@/utils/request';
import PaginationTest from '@/views/PaginationTest.vue';

export default {
    name: 'Cart',
  components: {
    PaginationTest
  },
    data() {

      return {
            user: {},
            uploadData: {},
            ap_details: {},

            total: 0,
            currentPage: 1,
            pageSize: 10,
            courseName: "",
            tableData: [],
            form: {

            },
            dialogFormVisible: false,
            ApplyFormVisible: false,
            application: {},
            cur_row: {},
            rules: {
              enrolmentDetailsSemester: [
                { required: true, message: 'Please input enrolment details for the semester', trigger: 'blur' }
              ],
              resume: [
                { required: true, message: 'Please upload your CV', trigger: 'change' },


              ],
              tranciption: [
                { required: true, message: 'Please upload the Academic record', trigger: 'change' },

              ],
              workingHours: [
                { required: true, message: 'Please input working hours', trigger: 'blur' },
                { type: 'number', message: 'Working hours must be a number', trigger: 'blur' },
                { type: 'number', min: 1, message: 'Working hours must be 1 or more', trigger: 'blur' }

              ],
              overseas: [
                { required: true, message: 'Please select if overseas', trigger: 'blur' }
              ],
              willback: [
                { required: true, message: 'Please select if you will be back', trigger: 'blur' }
              ],
              permanent: [
                { required: true, message: 'Please select if permanent', trigger: 'blur' }
              ],
              visa: [
                { required: true, message: 'Please select visa details', trigger: 'blur' }
              ],
              contracts: [
                { required: true, message: 'Please select contract details', trigger: 'blur' }
              ],
              description: [
                { required: true, message: 'Please provide a description', trigger: 'blur' }
              ],
              undergraduate: [
                { required: true, message: 'Please select undergraduate or Postgraduate', trigger: 'blur' }
              ],
              grades: [
                { required: true, message: 'Please select grades', trigger: 'blur' }
              ],
              studied: [
                { required: true, message: 'Please choose whether you have studied?', trigger: 'blur' }
              ],

              qualification: [
                { required: true, message: 'Please input qualification details', trigger: 'blur' }
              ],
              experience: [
                { required: true, message: 'Please input your experience', trigger: 'blur' }
              ]

              // ... 其他规则
            },
        }
    },
    methods: {
        load() {
            request.get("/cart", {
                params: {
                    auid: this.user.auid,
                    cur: this.currentPage,
                    size: this.pageSize,
                  cname: this.courseName,

                }
            }).then((res) => {
              console.log(res);
              this.tableData = res.data.data;
              this.total = res.data.total;

            })
                .catch((error) => {
                  console.error("Error loading data:", error);
                });
        },
        beforeFileUpload(file) {
          const isPDF = file.type === 'application/pdf';
          if (!isPDF) {
            this.$message.error('Only support for pdf');
          }
          return isPDF;
        },
        showDetails(row) {
          console.log(row);
          this.dialogFormVisible = true;
          this.form = row;
        },

      addApply(row) {
            this.ApplyFormVisible = true
            this.application.cid = row.cid

            this.uploadData.userId = this.user.auid,
            this.cur_row = row
        },
        sliderChange(row) {
          request.put("/cart/addcart", row).then(res => {
            this.load()
          })
        },

      filter() {
        request.get("/cart/search", {
          params: {
            auid: this.user.auid,// 添加这一行
            cname: this.courseName,
            cur: this.currentPage,
            size: this.pageSize,

          }
        }).then(res => {
          this.tableData = res.data.data;
          this.total = res.data.total;


        }).catch(error => {
          console.error("Error fetching data:", error);
          this.$message.error('Error fetching data!.');
        });
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

        reset() {
          this.courseName = ""; // 重置输入框
          this.load(); // 重新加载全部数据
        },


        submit() {
          this.$refs.form.validate((valid) => {
            if (valid) {
              // 当验证成功时执行以下操作
              this.application.auid = this.user.auid;
              this.application.status = "Under consideration";
              this.cur_row.applied = true;

              request.put("./apply/addapply", this.application).then(res => {
                if (res.code == 0) {
                  this.$message.success("successful upload!");
                }
                this.exit();
              });
              request.put("/cart/addcart", this.cur_row).then(res => {
                // 根据需要处理响应
              });
            } else {
              // 当验证失败时执行以下操作，例如显示一个错误消息
              this.$message.error("Please complete the form correctly before submitting.");
              return false;
            }
          });
        },


      uploadSuccess(res) {
            this.application.resume = res.data
            this.$message.success("success submit!")
        },
        uploadSuccess1(res) {
            this.application.tranciption = res.data
            this.$message.success("success submit!")
        },
        exit() {
            this.ApplyFormVisible = false
            this.application = {}
        }
    },

    created() {
        try {
            this.user = JSON.parse(sessionStorage.getItem('user')) || {};
        } catch (e) {
            console.error("Error parsing 'user' from sessionStorage", e);
            this.user = {};
        }
        this.load()

    }
}

</script>
