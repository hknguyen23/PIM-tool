import { MIN_DATE, MAX_DATE } from '../Constants/config.json';

export default {
  messages: {
    success: 'Fr Sucessfully proceed',
    notFound: 'Fr Not Found',
    fail: 'Fr Fail to proceed',
    projectNumberAlreadyExists: 'Fr The project number already existed',
    projectNumberAlreadyExistsPleaseChooseAnother: 'Fr The project number already existed. Please select a different project number',
    visasDoNotExist: 'Fr The following visas do not exist: ',
    versionMissMatched: 'Fr Miss match version, please refresh the page',
    errorWhileFetchingGroups: 'Fr Some errors happened while fetching for groups data',
    errorWhileFetchingEmployees: 'Fr Some errors happened while fetching for employees data'
  },
  header: {
    name: 'Gestion des informations de projet',
    help: 'Aider',
    login: 'S\'identifier',
    logout: 'Se déconnecter'
  },
  navigation: {
    title: 'Liste des projets',
    new: 'Nouveau',
    project: 'Projet',
    customer: 'Client',
    supplier: 'Le fournisseur'
  },
  errorScreen: {
    unexpected: 'Une erreur inattendue s\'est produite',
    please: 'S\'il te plaît',
    contact: 'contactez votre administrateur',
    or: 'ou alors',
    back: 'retour au projet de recherche'
  },
  searchProject: {
    title: 'Liste des projets',
    table: {
      numberColumn: 'Nombre',
      nameColumn: 'Nom',
      statusColumn: 'Statut',
      customerColumn: 'Client',
      startDateColumn: 'Date de début',
      deleteColumn: 'Supprimez',
      noData: 'Fr for No data',
      rowsPerPage: 'Fr for Rows per page:',
      noRowsSelected: 'Fr for No rows selected',
      row: 'Fr for row',
      rows: 'Fr for rows',
      selected: 'Fr for selected'
    },
    deleteConfirmDialog: {
      title: 'Fr Delete Projects Confirmation',
      content: 'Fr Are you sure you want to delete the selected project(s)?',
      cancelButton: 'Fr Cancel',
      deleteButton: 'Fr Delete'
    },
    searchFieldLabel: 'Fr Project number, name, customer name',
    comboBoxLabel: 'Fr Project status',
    searchButton: 'Fr Search Project',
    resetButton: 'Fr Reset Search',
    deleteAllButton: 'Fr Delete all selected projects',
    cannotDelete: 'Fr Only project with "NEW" status can be deleted'
  },
  projectManagement: {
    newProjectTitle: 'Nouveau Projet',
    updateProjectTitle: 'Fr Edit Project Information',
    form: {
      projectNumber: 'Fr Project Number',
      projectName: 'Fr Project name',
      customer: 'Fr Customer',
      group: {
        label: 'Fr Group',
        add: 'Fr Add',
        groupComboBoxLabel: 'Fr Choose a group',
        dialog: {
          title: 'Fr Add a new group',
          label: 'Fr Group name',
          cancelButton: 'Fr Cancel',
          addButton: 'Fr Add'
        }
      },
      members: 'Fr Members',
      status: 'Fr Status',
      startDate: 'Fr Start date',
      endDate: 'Fr End date',
      cancelButton: 'Fr Cancel',
      createButton: 'Fr Create Project',
      updateButton: 'Fr Update Project',
      topErrorMessage: 'Fr There\'re some errors, please check all the mandatory and error fields (*)',
      errors: {
        maximumLength: '* Fr This field can only has value of length 50',
        emptyField: '* Fr This field can\'t be empty',
        NaN: '* Fr This field must be a number',
        noItemSelected: '* Fr Please choose one item from the list',
        invalidDate: '* Fr Invalid date',
        endDateIsEqualOrLessThanStartDate: '* Fr End date can\'t be equal or less than Start date',
        minimalDate: `* Fr Date should not be before ${MIN_DATE}`,
        maximalDate: `* Fr Date should not be after ${MAX_DATE}`
      },
      working: 'Fr Working...'
    }
  }
}