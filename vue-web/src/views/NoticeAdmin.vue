<template>
  <div>
    <div style="padding: 10px;">

      <h4 style="color: rgba(0,70,127);">Notice</h4>
    </div>

    <div>
      <el-date-picker
          v-model="filterDate"
          type="date"
          placeholder="Select a date"
          style="width: 300px; margin: 10px 5px;"
          format="yyyy-MM-dd"
      ></el-date-picker>

      <el-button
          size="medium"
          type="primary"
          style="margin-right: 2px;"
          @click="filterByDate()"
      >Filter</el-button>

      <el-button
          size="medium"
          type="warning"
          style="margin-right: 5px;"
          @click="resetFilter()"
      >Reset</el-button>

      <el-button size="medium" type="success" @click="addNotice">+ Add Notice</el-button>
    </div>

    <ul>
      <li v-for="item in notices" :key="item.nid" style="margin: 20px 20px; border: 1px solid #409EFF; padding: 20px 20px; border-radius: 20px; list-style: none;">
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <el-descriptions title="Notice" column="2">
            <el-descriptions-item label="Date">{{ item.date }}</el-descriptions-item>
            <el-descriptions-item label="ReleaserID">{{ item.auid }}</el-descriptions-item>
            <el-descriptions-item label="Content">{{ item.content }}</el-descriptions-item>
          </el-descriptions>
          <el-button size="small" type="danger" @click="deleteNotice(item.nid)">Delete</el-button>
        </div>
      </li>
    </ul>


    <!-- 隐藏的修改表单 -->
    <el-dialog title="Notice Release" :visible.sync="dialogEditVisible" width="800px">
      <el-form :model="table" :rules="rules" ref="form" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="auid">
              <el-input v-model="table.auid" autocomplete="off" disabled></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="Content" prop="content">
              <el-input type="textarea" v-model="table.content" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-button size="medium" type="primary" @click="releaseNotice">+ Add Notice</el-button>
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
import request from '@/utils/request'
import PaginationTest from '@/views/PaginationTest.vue';
export default {
  name: 'NoticeAdmin',
  components: {
    PaginationTest
  },
  data() {
    return {
      user: {},

      notices: [],
      dialogEditVisible: false,
      table: {
        content: ''
      },
      filterDate: null,
      total: 0,
      currentPage: 1,
      pageSize: 10,
      rules: {
        content: [
          { required: true, message: 'Content cannot be empty', trigger: ['blur', 'change'] }
        ]
      }
    }
  },
  methods: {
    load() {
      request.get("/notice/newest", {
        params: {
          cur: this.currentPage,
          size: this.pageSize,
        }
      }).then(res => {
        this.notices = res.data.records;
        this.total = res.data.total;
      })
    },
    addNotice() {
      this.dialogEditVisible = true
      this.table = {}
      this.table.auid = this.user.auid
    },
    releaseNotice() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          // 当内容验证通过后，执行原有的提交逻辑
          request.put("/notice/addnotice", this.table).then(res => {
            if (res.code == 0) {
              this.$message.success("Release a new notice!");
            }
            this.load();
            this.dialogEditVisible = false;
          });
        } else {
          // 如果验证不通过，显示提示信息
          this.$message.warning('Content cannot be empty');
        }
      });
    },

    filterByDate() {
      if (this.filterDate) {
        const dd = String(this.filterDate.getDate()).padStart(2, '0');
        const mm = String(this.filterDate.getMonth() + 1).padStart(2, '0'); // January is 0!
        const yyyy = this.filterDate.getFullYear();

        const formattedDate = yyyy + '-' + mm + '-' + dd;

        request.get("/notice/bydate", {
          params: {
            date: formattedDate,
            cur: this.currentPage,
            size: this.pageSize,
          }
        }).then(res => {
          this.notices = res.data.records;
          this.total = res.data.total;
        })
      } else {
        this.$message.warning("Please select a date to filter first!")
      }
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
    deleteNotice(nid) {
      // 弹出确认对话框，确认是否删除
      this.$confirm("Are you sure you want to delete this notice?", "Delete Notice", {
        confirmButtonText: "Delete",
        cancelButtonText: "Cancel",
        type: "warning"
      })
          .then(() => {
            // 用户确认删除，发送删除请求
            request.delete(`/notice/${nid}`)
                .then(res => {
                  if (res.code == 0) {
                    this.$message.success("Notice deleted successfully!");
                    // 刷新通知列表
                    this.load();
                  } else {
                    this.$message.error("Failed to delete notice.");
                  }
                })
                .catch(error => {
                  console.error("Error deleting notice:", error);
                  this.$message.error("Failed to delete notice.");
                });
          })
          .catch(() => {
            // 用户取消删除
            this.$message.info("Cancelled delete.");
          });
    },

    resetFilter() {
      this.filterDate = null
      this.load()
    }
  },
  created() {
    this.user = JSON.parse(sessionStorage.getItem('user'))
    this.load()
  }
}
</script>
