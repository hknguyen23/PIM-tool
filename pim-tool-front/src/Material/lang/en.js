export default {
  messages: {
    success: 'Sucessfully proceed',
    fail: 'Fail to proceed',
    projectNumberAlreadyExists: 'The project number already existed',
    projectNumberAlreadyExistsPleaseChooseAnother: 'The project number already existed. Please select a different project number',
    visasDoNotExist: 'The following visas do not exist: ',
    versionMissMatched: 'Miss match version, please refresh the page',
    errorWhileFetchingGroups: 'Some errors happened while fetching for groups data',
    errorWhileFetchingEmployees: 'Some errors happened while fetching for employees data'
  },
  header: {
    name: 'Project Information Management',
    help: 'Help',
    login: 'Log in',
    logout: 'Log out'
  },
  navigation: {
    title: 'Projects list',
    new: 'New',
    project: 'Project',
    customer: 'Customer',
    supplier: 'Supplier'
  },
  errorScreen: {
    unexpected: 'Unexpected error occurred',
    please: 'Please',
    contact: 'contact your administrator',
    or: 'or',
    back: 'back to search project'
  },
  searchProject: {
    title: 'Projects list',
    table: {
      numberColumn: 'Number',
      nameColumn: 'Name',
      statusColumn: 'Status',
      customerColumn: 'Customer',
      startDateColumn: 'Start Date',
      deleteColumn: 'Delete',
      noData: 'No data',
      rowsPerPage: 'Rows per page:',
      noRowsSelected: 'No rows selected',
      row: 'row',
      rows: 'rows',
      selected: 'selected'
    },
    deleteConfirmDialog: {
      title: 'Delete Projects Confirmation',
      content: 'Are you sure you want to delete the selected project(s)?',
      cancelButton: 'Cancel',
      deleteButton: 'Delete'
    },
    searchFieldLabel: 'Project number, name, customer name',
    comboBoxLabel: 'Project status',
    searchButton: 'Search Project',
    resetButton: 'Reset Search',
    deleteAllButton: 'Delete all selected projects',
    cannotDelete: 'Only project with "New" status can be deleted'
  },
  projectManagement: {
    newProjectTitle: 'New Project',
    updateProjectTitle: 'Edit Project Information',
    form: {
      projectNumber: 'Project Number',
      projectName: 'Project name',
      customer: 'Customer',
      group: {
        label: 'Group',
        add: 'Add',
        groupComboBoxLabel: 'Choose a group',
        dialog: {
          title: 'Add a new group',
          label: 'Group name',
          cancelButton: 'Cancel',
          addButton: 'Add'
        }
      },
      members: 'Members',
      status: 'Status',
      startDate: 'Start date',
      endDate: 'End date',
      cancelButton: 'Cancel',
      createButton: 'Create Project',
      updateButton: 'Update Project',
      topErrorMessage: 'There\'re some errors, please check all the mandatory and error fields (*)',
      errors: {
        emptyField: '* This field can\'t be empty',
        NaN: '* This field must be a number',
        noItemSelected: '* Please choose one item from the list',
        invalidDate: '* Invalid date',
        endDateIsEqualOrLessThanStartDate: '* End date can\'t be equal or less than Start date'
      },
      working: 'Working...'
    }
  }
}