<template>
    <div>
      <el-input  size="medium" placeholder="Input Course Name" style="width: 300px; margin: 10px 5px;"  suffix-icon="el-icon-search" v-model="courseName"></el-input>
      <el-button size="medium" type="primary" @click="filter()">Filter</el-button>
      <el-button size="medium" type="warning" @click="reset">Reset</el-button>


        <!-- 导入-->

        <el-button size="medium" type="success" @click="addCourse" v-if="user.priority == 1 ">+ Add Courses</el-button>
        <el-button size="medium" type="primary" @click="importC" v-if="user.priority == 1">+ Import Courses From Previous
            Term</el-button>
        <!-- 表格控件 -->
        <el-table :data="tableData">
            <el-table-column prop="cname" label="Course" width="100" sortable>
            </el-table-column>
            <el-table-column prop="term" label="Term" width="100" sortable> </el-table-column>
            <el-table-column prop="minGrade" label="Available marker hours" sortable width="250" align="center" >
            </el-table-column>
            <el-table-column prop="capacity" label="Estimated number of students" width="260" align="center" sortable>
            </el-table-column>

            <el-table-column  label="Details" width="100">
              <template slot-scope="scope">
                <el-link @click="showDetails(scope.row)">Details<i class="el-icon-view el-icon--right"></i></el-link>
              </template>
            </el-table-column>

          <el-table-column prop="visible" label="Visible" width="100" sortable>
                <template slot-scope="scope">
                    <el-switch v-model="scope.row.visible" active-color="#13ce66" inactive-color="#ff4949"
                        @change="changeVisible(scope.row)">
                    </el-switch>
                </template>
            </el-table-column>
            <el-table-column label="Operation">
                <template slot-scope="scope">
                    <el-button type="warning" @click="edit(scope.row)" style="margin-right: 10px">Edit</el-button>
                    <!-- <el-button type="danger">Delete</el-button> -->
                    <el-popconfirm confirm-button-text="Confirm" cancel-button-text="Consideration" icon="el-icon-info"
                        icon-color="red" title="Are you sure you want to delete the user?" @confirm="del(scope.row.cid)">
                        <el-button slot="reference" type="danger" v-if="user.priority == 1">Delete</el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>

        <!-- 分页控件
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage"
            :page-sizes="[5, 10, 15]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
        </el-pagination> -->

        <!-- 隐藏的表单 -->
        <el-dialog title="Course Details" :visible.sync="dialogFormVisible" width="750px">

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
                <el-button @click="sendNotice" type="primary">Send Notice</el-button>
                <el-button @click="dialogFormVisible = false">Exit</el-button>
            </div>
        </el-dialog>

        <!-- 隐藏的修改表单 -->
        <el-dialog title="Course" :visible.sync="dialogEditVisible" width="700px">
          <el-form :model="table" :rules="formRules" ref="editForm">
                <el-row :gutter="20" >
                    <el-col :span="12" v-if="user.priority == 1">
                        <el-form-item label="Course name" prop="cname">
                            <el-input v-model="table.cname" autocomplete="off"></el-input>
                        </el-form-item>
                    </el-col>


                    <el-col :span="12" v-if="user.priority == 1 || user.priority == 3">
                        <el-form-item label="Current enrolled students" prop="selected">
                            <el-input type="number" v-model="table.selected" autocomplete="off"></el-input>

                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20" v-if="user.priority == 1" >
                    <el-col :span="12">
                        <el-form-item label="Term" prop="term">
                            <el-select v-model="table.term" placeholder="select term" style="width: 100%" >
                                <el-option v-for="item in options" :key="item.value" :label="item.label"
                                    :value="item.value">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="Estimated number of students" prop="capacity">
                            <el-input type="number"  v-model="table.capacity" autocomplete="off"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20" v-if="user.priority == 1" >
                    <el-col :span="12">
                        <el-form-item label="Course Coordinator Name" prop="coursecoordinatorname">
                            <el-input v-model="table.coursecoordinatorname" autocomplete="off"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="Course Coordinator Email" prop="coursecoordinatoremail">
                            <el-input v-model="table.coursecoordinatoremail" autocomplete="off"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20" v-if="user.priority == 1">
                    <el-col :span="12">
                        <el-form-item label="Course Director Name" prop="coursedirectorname">
                            <el-input v-model="table.coursedirectorname" autocomplete="off"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="Course Director Email" prop="coursedirectoremail">
                            <el-input v-model="table.coursedirectoremail" autocomplete="off"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20" v-if="user.priority==1" >
                    <el-col :span="12">
                        <el-form-item label="Available marker hours" prop="minGrade">
                            <el-input type="number" v-model="table.minGrade" autocomplete="off"></el-input>
                        </el-form-item>
                    </el-col>


                <el-col :span="12">
                  <el-form-item label="Minimum Grade Requirement" prop="requirement">
                    <el-select v-model="table.requirement" placeholder="Plase select" style="width: 100%;">
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

                </el-col>

              </el-row>
                <el-row :gutter="20" v-if="user.priority==1">
                    <el-col :span="12">
                        <el-form-item style="margin-top: 20px; " label="Needs marker?" prop="marker">
                            <el-radio v-model="table.marker" :label="true">Yes</el-radio>
                            <el-radio v-model="table.marker" :label="false">No</el-radio>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row :gutter="20"  v-if="user.priority == 3">

                  <el-form-item label="Number and type of assessments" style="margin-left: 10px;width: 60%" prop="details">

                    <br>
                    <div style="display: flex; flex-direction: column;">
                      <el-select v-model="table.details" @change="($event === 'Please enter content') ? showInput = true : showInput = false" placeholder="Please enter content">
                        <el-option label="A.Programming assignments" value="Programming assignments"></el-option>
                        <el-option label="B.Theory/math" value="Theory/math"></el-option>
                        <el-option label="C.Style questions" value="Style questions"></el-option>
                        <el-option label="D.Reports/essays" value="Reports/essays"></el-option>
                        <el-option label="E.Weekly labs" value="Weekly labs (for CS111 markers mark weekly labs)"></el-option>
                        <el-option label="F.Other" value="Please enter content"></el-option>
                      </el-select>
                      <div v-if="showInput" style="margin-top: 10px;" >
                        <el-input v-model="table.details" placeholder="Please enter content" ></el-input>
                      </div>
                    </div>
                  </el-form-item>


                  <div style="display: flex; align-items: center;">
                    <!-- el-select item (75%) -->
                    <el-form-item label="Convert marker to tutor hours OR Convert tutor to marker hours" prop="courseCoordinator" style="flex: 3; margin-left: 10px; margin-right: 10px;">
                      <el-select v-model="table.courseCoordinator" STYLE="width: 100%">
                        <el-option label="A. Convert marker to tutor hours" value="Convert marker to tutor hours"></el-option>
                        <el-option label="B. Convert tutor to marker hours" value="Convert tutor to marker hours"></el-option>
                      </el-select>
                    </el-form-item>

                    <!-- hour input item (25%) -->
                    <el-form-item label="Convert hours" prop="hour" style="flex: 1; margin-left: 10px; margin-right: 10px;">
                      <el-input type="text" v-model="table.hour" autocomplete="off"></el-input>
                    </el-form-item>
                  </div>







                  <el-form-item label=" Marker’s responsibility for the course" style="margin-left: 10px;" prop="courseDirector">
                        <el-input type="textarea" v-model="table.courseDirector" autocomplete="off"></el-input>
                    </el-form-item>


                </el-row>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogEditVisible = false">Exit</el-button>
                <el-button type="primary" @click="editConfirm">Confirm</el-button>
            </div>
        </el-dialog>
      <PaginationTest :current-page="currentPage" :page-size="pageSize" :total="total" @change-page="handleCurrentChange" @change-size="handleSizeChange"></PaginationTest>

        <!-- 隐藏的导入课程表单 -->
        <el-dialog :visible.sync="importVisible">
            <el-row :gutter="20">
                <el-col :span="12">
                    <h4 style="margin-bottom: 20px; margin-left: 5px;">import from</h4>
                    <el-select v-model="selectTerm.pre" placeholder="select term" style="width: 100%">
                        <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
                        </el-option>
                    </el-select>
                </el-col>
                <el-col :span="12">
                    <h4 style="margin-bottom: 20px; margin-left: 5px;">import to</h4>
                    <el-select v-model="selectTerm.cur" placeholder="select term" style="width: 100%">
                        <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
                        </el-option>
                    </el-select>
                </el-col>
            </el-row>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="confirmImport">Confirm</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import request from "../utils/request.js";
import PaginationTest from '@/views/PaginationTest.vue';
export default {
    name: "DashboardAdmin",
  components: {
    PaginationTest
  },
    data() {
        return {
            courseName: "",
            importVisible: false,
            user: {},
            table: {
              details: ''
            },
            showInput: false,// 控制文本框的可见性
            tableData: [],
            total: 0,
          currentPage: 1,
          pageSize: 10,
            dialogFormVisible: false,
            dialogEditVisible: false,
            formLabelWidth: 50,
            form: {},
            cart: {},
            formRules: {
              cname: [{ required: true, message: 'Please input course name', trigger: 'blur' }],
              selected: [{ required: true, message: 'Please input enrolled students(number)', trigger: 'blur' }],
              term: [{ required: true, message: 'Please select term', trigger: 'change' }],
              capacity: [{ required: true, message: 'Please input capacity(number)', trigger: 'blur' }],
              coursecoordinatorname: [{ required: true, message: 'Please input course coordinator name', trigger: 'blur' }],
              coursecoordinatoremail: [
                { required: true, message: 'Please input course coordinator email', trigger: 'blur' },
                { type: 'email', message: 'Please input correct email address', trigger: 'blur' }
              ],
              coursedirectorname: [{ required: true, message: 'Please input course director name', trigger: 'blur' }],
              coursedirectoremail: [
                { required: true, message: 'Please input course director email', trigger: 'blur' },
                { type: 'email', message: 'Please input correct email address', trigger: 'blur' }
              ],
              minGrade: [{ required: true, message: 'Please input available marker hours', trigger: 'blur' }],
              requirement: [{ required: true, message: 'Please select minimum grade', trigger: 'blur' }],
              marker: [{ required: true, message: 'Please select needs marker', trigger: 'blur' }],
              details: [{ required: true, message: 'Please select number and type of assessments', trigger: 'blur' }],
              courseCoordinator: [{ required: true, message: 'Please select', trigger: 'blur' }],
              hour: [{ required: true, message: 'Please input hours', trigger: 'blur' }],
              courseDirector: [{ required: true, message: 'Please input Marker‘s responsibility for the course', trigger: 'blur' }],
            },
            formValid: true,
            options: [
              { value: '2021,S1', label: '2021,S1' },
              { value: '2021,S2', label: '2021,S2' },
              { value: '2021,SS', label: '2021,SS' },
              { value: '2022,S1', label: '2022,S1' },
              { value: '2022,S2', label: '2022,S2' },
              { value: '2022,SS', label: '2022,SS' },
              { value: '2023,S1', label: '2023,S1' },
              { value: '2023,S2', label: '2023,S2' },
              { value: '2023,SS', label: '2023,SS' },
              { value: '2024,S1', label: '2024,S1' },
              { value: '2024,S2', label: '2024,S2' },
              { value: '2024,SS', label: '2024,SS' },
              { value: '2025,S1', label: '2025,S1' },
              { value: '2025,S2', label: '2025,S2' },
              { value: '2025,SS', label: '2025,SS' }
            ],

          selectTerm: {},
            notice: {}
        };
    },
    methods: {
      filter() {
        request
            .get("/course/page2", {
              params: {
                cur: this.currentPage,
                size: this.pageSize,
                cname: this.courseName,
                auid: this.user.auid, // 假设auid来自用户的session信息
                priority: 1 // 根据需要设置优先级


              },
            })
            .then((res) => {
              console.log(res);
              this.tableData = res.data.records;
              this.total = res.data.total;
            })
            .catch((error) => {
              console.error("Error loading filtered data:", error);
            });
      },

      load() {
        request
            .get("/course/page2", { // 注意这里，如果你的端点是 `/course`，你需要确保后端端点能够正确返回分页信息
              params: {
                cur: this.currentPage,
                size: this.pageSize,
                cname: this.courseName,
                auid: this.user.auid, // 假设auid来自用户的session信息
                priority: 1 // 根据需要设置优先级

              },
            })
            .then((res) => {
              console.log(res);
              this.tableData = res.data.records; // 注意这里应该是res.data.records
              this.total = res.data.total; // 设置total
            })
            .catch((error) => {
              console.error("Error loading data:", error);
            });
      },


      handleCurrentChange(val) {
            console.log(val);

            this.currentPage = val;
            this.load();
        },
        handleSizeChange(val) {
            this.pageSize = val;
            this.load();
        },
        showDetails(row) {
            this.dialogFormVisible = true;

            console.log(row);
            this.form = row;
        },
        edit(row) {
            this.dialogEditVisible = true;
            this.table = row;
        },
        editConfirm() {
          this.$refs.editForm.validate((valid) => {
            if (!valid) {
              this.formValid = false;
              return;
            }
            // 如果验证成功，则继续执行现有的逻辑。
            request.put("/course/update", this.table).then((res) => {
              if (res.code == 0) {
                this.$message.success("successful update");
              }
              this.table = {};
              this.dialogEditVisible = false;
              this.load();
            });
          });
        },

      reset() {
        this.courseName = ""
        this.load()

        },
        del(id) {
            request.delete("/course/del/" + id).then((res) => {
                if (res.code == 0) {
                    this.$message.success("successful delete!");
                }
                this.load();
            });
        },
        addCourse() {
            this.dialogEditVisible = true
            this.table = {}
        },
        importC() {
            this.importVisible = true
        },
      confirmImport() {
        console.log(this.selectTerm.pre);
        console.log(this.selectTerm.cur);
        request.post("/course/batchadd", null, {
          params: {
            term: this.selectTerm.pre,
            newTerm: this.selectTerm.cur
          }
        }).then(res => {
          if (res.code == 0) {
            this.$message.success("successful import!");
            this.load();
          } else {
            // 其他错误情况
            this.$message.error("There are no courses in this semester, unable to generate new academic year courses");
          }
          this.importVisible = false;
        });
      },

      changeVisible(row) {
            request.put("/course/update", row).then(res => {
                this.load()
            })
        },
        sendNotice() {
            this.notice.auid = this.user.auid
            this.notice.content = this.form.cname + "("+this.form.term +")"+ " has been chosen too many times, please choose accordingly!"
            request.put("/notice/addnotice", this.notice).then(res => {
                if (res.code == 0) {
                    this.$message.success("Release a new notice!")
                }
                this.dialogFormVisible = false
                this.load()
            })
        }
    },
    created() {
        this.user = JSON.parse(sessionStorage.getItem("user"));
        this.load();
    },

    setup() { },
};
</script>
