import { MIN_DATE, MAX_DATE } from '../Constants/config.json';

export default {
  messages: {
    success: 'Thành công',
    notFound: 'Không tìm thấy, vui lòng tải lại trang',
    fail: 'Thất bại',
    projectNumberAlreadyExists: 'Số dự án này đã tồn tại',
    projectNumberAlreadyExistsPleaseChooseAnother: 'Số dự án này đã tồn tại. Vui lòng chọn số dự án khác',
    visasDoNotExist: 'Visa(s) không tồn tại',
    versionMissMatched: 'Phiên bản hết hạn, vui lòng tải lại trang',
    errorWhileFetchingGroups: 'Có lỗi xảy ra khi lấy dữ liệu nhóm',
    errorWhileFetchingEmployees: 'Có lỗi xảy ra khi lấy dữ liệu nhân viên'
  },
  header: {
    name: 'Ứng dụng Quản lý thông tin dự án',
    help: 'Hỗ trợ',
    login: 'Đăng nhập',
    logout: 'Đăng xuất'
  },
  navigation: {
    title: 'Danh sách dự án',
    new: 'Tạo mới',
    project: 'Dự án',
    customer: 'Khách hàng',
    supplier: 'Nhà cung cấp'
  },
  errorScreen: {
    unexpected: 'Có lỗi xảy ra',
    please: 'Vui lòng',
    contact: 'liên hệ với admin của trang web',
    or: 'hoặc',
    back: 'trở lại màn hình trang chủ'
  },
  searchProject: {
    title: 'Danh sách dự án',
    table: {
      numberColumn: 'Số dự án',
      nameColumn: 'Tên dự án',
      statusColumn: 'Trạng thái',
      customerColumn: 'Khách hàng',
      startDateColumn: 'Ngày bắt đầu',
      deleteColumn: 'Xóa',
      noData: 'Không có dữ liệu',
      rowsPerPage: 'Số dòng mỗi trang:',
      noRowsSelected: 'Không có dòng nào được chọn',
      row: 'dòng',
      rows: 'dòng',
      selected: 'được chọn'
    },
    deleteConfirmDialog: {
      title: 'Xác nhận xóa dự án',
      content: 'Bạn có chắc muốn xóa dự án đã chọn này không?',
      cancelButton: 'Hủy',
      deleteButton: 'Xóa'
    },
    searchFieldLabel: 'Số dự án, tên dự án, tên khách hàng',
    comboBoxLabel: 'Trạng thái dự án',
    searchButton: 'Tìm kiếm dự án',
    resetButton: 'Tải lại danh sách',
    deleteAllButton: 'Xóa tất cả các dự án được chọn',
    cannotDelete: 'Chỉ có dự án có trạng thái "NEW" thì mới được xóa'
  },
  projectManagement: {
    newProjectTitle: 'Tạo dự án mới',
    updateProjectTitle: 'Cập nhật thông tin dự án',
    form: {
      projectNumber: 'Số dự án',
      projectName: 'Tên dự án',
      customer: 'Khách hàng',
      group: {
        label: 'Nhóm',
        groupComboBoxLabel: 'Chọn một nhóm',
        add: 'Thêm',
        dialog: {
          title: 'Thêm mới một nhóm',
          label: 'Tên nhóm',
          cancelButton: 'Hủy',
          addButton: 'Thêm'
        }
      },
      members: 'Thành viên',
      status: 'Trạng thái dự án',
      startDate: 'Ngày bắt đầu',
      endDate: 'Ngày kết thúc',
      cancelButton: 'Hủy',
      createButton: 'Tạo',
      updateButton: 'Cập nhật',
      topErrorMessage: 'Có lỗi xảy ra, vui lòng nhập đầy đủ thông tin các vùng bắt buộc và đang báo lỗi (*)',
      errors: {
        maximumLength: '* Độ dài dữ liệu vùng này không vượt quá 50 ký tự',
        emptyField: '* Vùng này không được để trống',
        NaN: '* Dữ liệu phải là một con số',
        noItemSelected: '* Vui lòng chọn một lựa chọn trong danh sách',
        invalidDate: '* Ngày không hợp lệ',
        endDateIsEqualOrLessThanStartDate: '* Ngày kết thúc không thể bằng hoặc nhỏ hơn ngày bắt đầu',
        minimalDate: `* Ngày không được nhỏ hơn ngày ${MIN_DATE}`,
        maximalDate: `* Ngày không được lớn hơn ngày ${MAX_DATE}`
      },
      working: 'Đang chạy...'
    }
  }
}