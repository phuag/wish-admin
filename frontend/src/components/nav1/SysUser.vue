<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-cascader filterable clearable change-on-select v-model="filters.office" :options="officeOptions" :props="props" placeholder="所在部门">
          </el-cascader>
        </el-form-item>
        <el-form-item>
          <el-input v-model="filters.name" placeholder="登录名或姓名" suffix-icon="search"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getSysUsers">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary">导出</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary">导入</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-if="hasPerm('sysUser:add')" @click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table :data="sysUsers" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column type="index" width="60">
      </el-table-column>
      <el-table-column prop="loginName" label="登录名" width="120" sortable>
      </el-table-column>
      <el-table-column prop="name" label="姓名" width="120" sortable>
      </el-table-column>
      <el-table-column prop="sex" label="性别" width="100" sortable>
      </el-table-column>
      <el-table-column prop="birth" label="生日" width="120" :formatter="formatBirth" sortable>
      </el-table-column>
      <el-table-column prop="officeNameWithPath" label="所在部门" min-width="160" sortable>
      </el-table-column>
      <el-table-column label="操作" width="150">
        <template slot-scope="scope">
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--工具条-->
    <el-col :span="24" class="toolbar">
      <el-button type="danger" @click="batchRemove" :disabled="this.sels.length===0">批量删除</el-button>
      <el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="pageSize" :total="total" style="float:right;">
      </el-pagination>
    </el-col>

    <!--编辑界面-->
    <el-dialog title="编辑" :visible.sync="editFormVisible" :close-on-click-modal="false">
      <el-form :model="editForm" label-width="100px" :rules="editFormRules" ref="editForm">
        <el-form-item label="所在部门" prop="officeIds">
          <el-cascader clearable change-on-select v-model="editForm.officeIds" :options="officeOptions" :props="props" placeholder="所在部门">
          </el-cascader>
        </el-form-item>
        <el-form-item label="登录名" prop="loginName">
          <el-input v-model="editForm.loginName" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="editForm.name" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="editForm.sex">
            <el-radio class="radio" label="男">男</el-radio>
            <el-radio class="radio" label="女">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="生日">
          <el-date-picker type="date" placeholder="选择日期" v-model="editForm.birth"></el-date-picker>
        </el-form-item>
        <el-form-item label="是否允许入网">
          <el-radio-group v-model="editForm.loginFlag">
            <el-radio class="radio" label="1">是</el-radio>
            <el-radio class="radio" label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="创建时间">
          <el-input v-model="editForm.createDate" :disabled="true">
          </el-input>
        </el-form-item>
        <el-form-item label="最后登陆:">
          <label class="lbl">IP: {{editForm.loginIp}}&nbsp;&nbsp;&nbsp;&nbsp;时间：{{ fomatDate(editForm.loginDate)}}</label>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="editFormVisible = false">取消</el-button>
        <el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
      </div>
    </el-dialog>
    <!--新增界面-->
    <el-dialog title="新增" :visible.sync="addFormVisible" :close-on-click-modal="false">
      <el-form :model="addForm" label-width="100px" :rules="addFormRules" ref="addForm">
        <el-form-item label="所在部门" prop="officeIds">
          <el-cascader clearable change-on-select v-model="addForm.officeIds" :options="officeOptions" :props="props" placeholder="所在部门">
          </el-cascader>
        </el-form-item>
        <el-form-item label="登录名" prop="loginName">
          <el-input v-model="addForm.loginName" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="addForm.name" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="addForm.sex">
            <el-radio class="radio" label="男">男</el-radio>
            <el-radio class="radio" label="女">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="生日">
          <el-date-picker type="date" placeholder="选择日期" v-model="addForm.birth"></el-date-picker>
        </el-form-item>
        <el-form-item label="是否允许入网">
          <el-radio-group v-model="addForm.loginFlag">
            <el-radio class="radio" label="1">是</el-radio>
            <el-radio class="radio" label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="addFormVisible = false">取消</el-button>
        <el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
      </div>
    </el-dialog>
  </section>
</template>

<script>
import util from '../../common/js/util'
// import NProgress from 'nprogress'
import { getSysUserListPage, getOfficeList, removeSysUser, batchRemoveUser, editSysUser, addSysUser, checkLoginName } from '../../api/api'
import router from '../../router'

export default {
  data () {
    var validateUnique = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('请输入登录名'))
      }
      let para = {
        loginName: value
      }
      checkLoginName(para).then((res) => {
        if (res.data === false) {
          callback(new Error('请更换为唯一的登录名'))
        } else {
          callback()
        }
      }).catch(err => {
        console.log(err)
      })
    }

    return {
      filters: {
        office: [],
        name: ''
      },
      officeOptions: [],
      props: {
        value: 'id',
        label: 'name',
        children: 'offices'
      },
      sysUsers: [],
      total: 0,
      page: 1,
      pageSize: 10,
      listLoading: false,
      sels: [], // 列表选中列

      editFormVisible: false, // 编辑界面是否显示
      editLoading: false,
      editFormRules: {
        officeIds: [
          { type: 'array', required: true, message: '请选择部门', trigger: 'change' }
        ],
        loginName: [
          { required: true, message: '请输入登录名', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请输入姓名', trigger: 'blur' }
        ]
      },
      // 编辑界面数据
      editForm: {
        id: 0,
        name: '',
        sex: -1,
        birth: '',
        officeIds: [],
        loginIp: '',
        loginDate: ''
      },

      addFormVisible: false, // 新增界面是否显示
      addLoading: false,
      addFormRules: {
        officeIds: [
          { type: 'array', required: true, message: '请选择部门', trigger: 'change' }
        ],
        loginName: [
          // { required: true, message: '请输入登录名', trigger: 'blur' },
          { required: true, validator: validateUnique, trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请输入姓名', trigger: 'blur' }
        ]
      },
      // 新增界面数据
      addForm: {
        loginName: '',
        name: '',
        sex: '',
        birth: '',
        loginFlag: '0',
        officeIds: []
      }

    }
  },
  methods: {
    // 性别显示转换
    formatSex: function (row, column) {
      return row.sex === '男' ? '男' : row.sex === '女' ? '女' : '未知'
    },
    formatBirth: function (row, column) {
      return util.formatDate.format(new Date(row.birth))
    },
    fomatDate: function (val) {
      return util.formatDate.format(new Date(val), 'yyyy-MM-dd hh:mm:ss')
    },
    handleCurrentChange (val) {
      this.page = val
      this.getSysUsers()
    },
    handleItemChange (val) {
      console.log('active item:', val)
      let para = {
        officeId: val[val.length - 1]
      }
      getOfficeList(para).then((res) => {
        this.officeOptions.forEach((v, i) => {
          v.offices = res.data.map(vv => {
            return {
              name: vv.name,
              id: vv.id
            }
          })
        })
      }).catch(err => {
        console.log(err)
      })
      // setTimeout(_ => {
      //   if (val.indexOf('江苏') > -1 && !this.officeOptions[0].offices.length) {
      //     this.officeOptions[0].offices = [{
      //       label: '南京'
      //     }]
      //   } else if (val.indexOf('浙江') > -1 && !this.officeOptions[1].offices.length) {
      //     this.officeOptions[1].offices = [{
      //       label: '杭州'
      //     }]
      //   }
      // }, 300)
    },
    initOfficeOptions () {
      // let para = {
      //   officeId: 0
      // }
      getOfficeList().then((res) => {
        this.officeOptions = util.toTree(res.data, 'id', 'parentId', 'offices')
        // this.officeOptions = res.data.map(v => {
        //   return {
        //     name: v.name,
        //     id: v.id,
        //     offices: []
        //   }
        // })
      }).catch(err => {
        console.log(err)
      })
    },
    // 获取用户列表
    getSysUsers () {
      let para = {
        page: this.page,
        size: this.pageSize,
        office: this.filters.office[this.filters.office.length - 1],
        q: this.filters.name
      }
      this.listLoading = true
      // NProgress.start()
      getSysUserListPage(para).then((res) => {
        this.total = res.data.total
        this.sysUsers = res.data.list
        this.listLoading = false
        // NProgress.done()
      }).catch(err => {
        console.log(err)
        router.push({ path: '/login' })
      })
    },
    // 删除
    handleDel: function (index, row) {
      this.$confirm('确认删除该记录吗?', '提示', {
        type: 'warning'
      }).then(() => {
        this.listLoading = true
        // NProgress.start()
        let para = { id: row.id }
        removeSysUser(para).then((res) => {
          this.listLoading = false
          // NProgress.done()
          this.$message({
            message: '删除成功',
            type: 'success'
          })
          this.getSysUsers()
        })
      }).catch(() => {

      })
    },
    // 显示编辑界面
    handleEdit: function (index, row) {
      this.editForm = Object.assign({}, row)
      // 按照el-cascader数据格式要求，指定该对象绑定要求
      this.editForm.officeIds = this.editForm.office.parentIds.split(',')
      this.editForm.officeIds.push(this.editForm.office.id)
      this.editFormVisible = true
    },
    // 显示新增界面
    handleAdd: function () {
      this.addFormVisible = true
      this.addForm = {
        name: '',
        sex: -1,
        birth: '',
        addr: ''
      }
    },
    // 编辑
    editSubmit: function () {
      this.$refs.editForm.validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.editLoading = true
            // NProgress.start()
            let para = Object.assign({}, this.editForm)
            para.birth = (!para.birth || para.birth === '') ? '' : util.formatDate.format(new Date(para.birth), 'yyyy-MM-dd')
            para.officeId = para.officeIds.pop()
            editSysUser(para).then((res) => {
              this.editLoading = false
              // NProgress.done()
              this.$message({
                message: '提交成功',
                type: 'success'
              })
              this.$refs['editForm'].resetFields()
              this.editFormVisible = false
              this.getSysUsers()
            })
          })
        }
      })
    },
    // 新增
    addSubmit: function () {
      this.$refs.addForm.validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.addLoading = true
            // NProgress.start()
            let para = Object.assign({}, this.addForm)
            para.birth = (!para.birth || para.birth === '') ? '' : util.formatDate.format(new Date(para.birth), 'yyyy-MM-dd')
            para.officeId = para.officeIds.pop()
            addSysUser(para).then((res) => {
              this.addLoading = false
              // NProgress.done()
              this.$message({
                message: '提交成功',
                type: 'success'
              })
              this.$refs['addForm'].resetFields()
              this.addFormVisible = false
              this.getSysUsers()
            })
          })
        }
      })
    },
    selsChange: function (sels) {
      this.sels = sels
    },
    // 批量删除
    batchRemove: function () {
      var ids = this.sels.map(item => item.id).toString()
      this.$confirm('确认删除选中记录吗？', '提示', {
        type: 'warning'
      }).then(() => {
        this.listLoading = true
        // NProgress.start()
        let para = { ids: ids }
        batchRemoveUser(para).then((res) => {
          this.listLoading = false
          // NProgress.done()
          this.$message({
            message: '删除成功',
            type: 'success'
          })
          this.getSysUsers()
        })
      }).catch(() => {

      })
    }
  },
  mounted () {
    this.getSysUsers()
    this.initOfficeOptions()
  }
}
</script>

<style scoped>

</style>
