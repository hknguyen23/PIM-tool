import React, { useEffect, useState } from 'react';
import { useHistory, useLocation } from 'react-router-dom';
import Translate from 'react-translate-component';
import {
  Divider,
  Button,
  TextField,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogContentText,
  DialogActions,
  CircularProgress,
  Snackbar,
} from '@material-ui/core';
import Autocomplete, { createFilterOptions } from '@material-ui/lab/Autocomplete';
import Alert from '@material-ui/lab/Alert';
import moment from 'moment';
import MomentUtils from '@date-io/moment';
import { KeyboardDatePicker as DatePicker, MuiPickersUtilsProvider } from '@material-ui/pickers';
import { makeStyles } from '@material-ui/core/styles';
import { DATE_FORMAT } from '../Constants/config.json';
import { fetchGroups } from '../Services/groupService';
import { fetchEmployees } from '../Services/employeeService';
import { createProject, updateProject } from '../Services/projectService';
import codeAndMessage from '../Constants/codeAndMessage';

const useStyles = makeStyles((theme) => ({
  form: {
    display: 'flex',
    flexDirection: 'column',
    width: 940
  },
  grow: {
    flexGrow: 1
  },
  formItem: {
    display: 'flex',
    padding: 19
  },
  required: {
    color: 'red',
    fontSize: '18pt'
  },
  label: {
    width: 190,
    fontSize: '14pt',
    fontFamily: 'Segoe UI',
    fontWeight: 500,
    color: '#666666'
  },
  formTitle: {
    fontSize: '18pt',
    fontFamily: 'Segoe UI',
    fontWeight: 500,
    color: '#666666'
  },
  shortInputField: {
    width: 240
  },
  longInputField: {
    width: 710
  },
  datePicker: {
    border: '1px solid #ccc',
    padding: 5,
    borderRadius: 4,
    width: 240,
    height: 40
  },
  button: {
    padding: 0,
    marginLeft: 50,
    width: 210,
    height: 35,
    fontSize: '14pt',
    borderRadius: 4,
    lineHeight: 'normal',
    textTransform: 'none'
  },
  helperTextForDatePicker: {
    display: 'flex',
    marginLeft: 210,
    marginTop: -30,
    paddingRight: 19,
  },
  errorLabel: {
    paddingLeft: 14,
    paddingTop: 4,
    width: 240,
    fontSize: '10pt',
    fontFamily: 'Segoe UI',
    color: 'red'
  }
}));

const projectStatuses = [
  { value: 'NEW', title: 'New' },
  { value: 'PLA', title: 'Planned' },
  { value: 'INP', title: 'In process' },
  { value: 'FIN', title: 'Finished' }
];

export default function ProjectManagement({ mode }) {
  const classes = useStyles();
  const filter = createFilterOptions();
  const history = useHistory();
  const location = useLocation();
  const delay = ms => new Promise(res => setTimeout(res, ms));

  const [groupName, setGroupName] = useState('');
  const [groups, setGroups] = useState([]);
  const [employees, setEmployees] = useState([]);

  const [values, setValues] = useState({
    version: '',
    projectNumber: '',
    projectName: '',
    customer: '',
    group: null,
    members: [],
    status: projectStatuses[0],
    startDate: new Date(),
    endDate: null
  });

  const [errors, setErrors] = useState({
    projectNumber: '',
    projectName: '',
    customer: '',
    group: '',
    members: '',
    status: '',
    startDate: '',
    endDate: ''
  });

  const [isHiddenErrorMessage, setIsHiddenErrorMessage] = useState(true);
  const [openProcessWaitingDialog, setOpenProcessWaitingDialog] = useState(false);
  const [openCreateGroupDialog, setOpenCreateGroupDialog] = useState(false);
  const [openSnackbar, setOpenSnackbar] = useState(false);
  const [isSuccessRequest, setIsSuccessRequest] = useState(false);
  const [code, setCode] = useState(0);

  useEffect(() => {
    fetchGroups()
      .then(res => {
        console.log(res);
        setCode(res.code);
        if (res.code === 200) {
          setGroups(res.data.data);
        } else {
          history.push("/error", { errorMessage: res.message });
          setGroups([]);
        }
      })
      .catch(error => {
        console.log(error);
      });
  }, []);

  useEffect(() => {
    fetchEmployees()
      .then(res => {
        console.log(res);
        setCode(res.code);
        if (res.code === 200) {
          setEmployees(res.data.data);
        } else {
          history.push("/error", { errorMessage: res.message });
          setEmployees([]);
        }
      })
      .catch(error => {
        console.log(error);
      });
  }, []);

  useEffect(() => {
    if (mode === "update") {
      const temp = location.state.currentProject;
      console.log(temp);
      setValues({
        ...values,
        version: temp.version,
        projectNumber: temp.projectNumber,
        projectName: temp.projectName,
        customer: temp.customer,
        status: temp.status,
        group: temp.group,
        members: temp.employees,
        startDate: temp.startDate,
        endDate: temp.endDate
      });
    }
  }, []);

  // change textfield
  const handleChangeValues = (e) => {
    setValues({
      ...values,
      [e.target.name]: e.target.value
    });
  }

  // validate textfield
  const handleValidate = (e) => {
    if (!e.target.value) {
      switch (e.target.name) {
        case 'projectNumber':
        case 'projectName':
        case 'customer':
          setErrors({
            ...errors,
            [e.target.name]: <Translate content="projectManagement.form.errors.emptyField" />
          });
          break;

        case 'group':
        case 'status':
          setErrors({
            ...errors,
            [e.target.name]: <Translate content="projectManagement.form.errors.noItemSelected" />
          });
          break;
      }
    } else {
      switch (e.target.name) {
        case 'projectNumber':
          setErrors({
            ...errors,
            [e.target.name]: isNaN(e.target.value) ? <Translate content="projectManagement.form.errors.NaN" /> : ""
          });
          break;

        default:
          setErrors({
            ...errors,
            [e.target.name]: ""
          });
      }
    }
  }

  const handleCloseCreateGroupDialog = () => {
    setGroupName('');
    setOpenCreateGroupDialog(false);
  }

  const handleCloseProcessWaitingDialog = () => {
    setOpenProcessWaitingDialog(false);
  }

  const handleCloseSnackbar = () => {
    setOpenSnackbar(false);
  }

  const handleSubmitNewGroup = (e) => {
    e.preventDefault();
    setValues({
      ...values,
      group: groupName
    });
    handleCloseCreateGroupDialog();
  }

  const handleSubmitProject = async (e) => {
    e.preventDefault();

    var tempErrors = { ...errors };
    if (values.projectNumber === '') {
      tempErrors.projectNumber = <Translate content="projectManagement.form.errors.emptyField" />;
    }
    if (values.projectName === '') {
      tempErrors.projectName = <Translate content="projectManagement.form.errors.emptyField" />;
    }
    if (values.customer === '') {
      tempErrors.customer = <Translate content="projectManagement.form.errors.emptyField" />;
    }
    if (values.group === null) {
      tempErrors.group = <Translate content="projectManagement.form.errors.noItemSelected" />;
    }
    if (values.status === null) {
      tempErrors.status = <Translate content="projectManagement.form.errors.noItemSelected" />;
    }
    if (values.startDate === null) {
      tempErrors.startDate = <Translate content="projectManagement.form.errors.emptyField" />;
    }
    if (values.endDate !== null) {
      const start = new Date(values.startDate);
      const end = new Date(values.endDate);
      if (start >= end) {
        tempErrors.endDate = <Translate content="projectManagement.form.errors.endDateIsEqualOrLessThanStartDate" />;
      }
    }
    setErrors(tempErrors);

    var errorList = Object.entries(tempErrors);
    const hasError = errorList.filter((v) => v[1] !== '').length > 0;
    if (hasError) {
      console.log(tempErrors);
      setIsHiddenErrorMessage(false);
      window.scrollTo(0, 0);
    } else {
      setOpenProcessWaitingDialog(true);
      let data = {
        version: +values.version,
        projectNumber: +values.projectNumber,
        projectName: values.projectName,
        customer: values.customer,
        status: values.status.value,
        group: values.group,
        employeeIds: values.members.map(member => member.id),
        startDate: values.startDate,
        endDate: values.endDate
      }
      console.log(data);

      let isError = false;

      if (mode === "new") {
        createProject(data)
          .then(res => {
            console.log(res);
            setCode(res.code);
            if (res.code === 200) {
              setIsSuccessRequest(true);
            } else {
              if (res.code === 101) {
                setErrors({
                  ...errors,
                  projectNumber: codeAndMessage.get(res.code)
                });
              }
              setIsSuccessRequest(false);
              isError = true;
            }
          })
          .catch(error => {
            console.log(error);
          });
      } else if (mode === "update") {
        data = {
          ...data,
          id: location.state.currentProject.id
        }
        updateProject(data)
          .then(res => {
            console.log(res);
            setCode(res.code);
            if (res.code === 200) {
              setIsSuccessRequest(true);
            } else {
              setIsSuccessRequest(false);
              isError = true;
            }
          })
          .catch(error => {
            console.log(error);
          });
      }

      await delay(3000);
      setOpenProcessWaitingDialog(false);
      setOpenSnackbar(true);
      await delay(3000);

      if (!isError) {
        history.push("/");
      }
    }
  }

  const handleCancel = () => {
    history.push("/");
  }

  return (
    <React.Fragment>
      <p className={classes.formTitle}>
        <Translate content={mode === "new" ? "projectManagement.newProjectTitle" : "projectManagement.updateProjectTitle"} />
      </p>

      <Divider />

      <Alert severity="error" hidden={isHiddenErrorMessage}>
        <Translate content="projectManagement.form.topErrorMessage" />
      </Alert>

      <p></p>

      <div>
        <form className={classes.form} noValidate onSubmit={handleSubmitProject}>
          <div className={classes.formItem}>
            <p className={classes.label}>
              <Translate content="projectManagement.form.projectNumber" />
              <span className={classes.required}> *</span>
            </p>

            <TextField
              className={classes.shortInputField}
              value={values.projectNumber}
              disabled={mode === "update"}
              id="project-number"
              name='projectNumber'
              variant="outlined"
              size='small'
              error={errors.projectNumber !== '' ? true : false}
              helperText={errors.projectNumber}
              required
              onChange={handleChangeValues}
              onBlur={handleValidate}
            />
          </div>

          <div className={classes.formItem}>
            <p className={classes.label}>
              <Translate content="projectManagement.form.projectName" />
              <span className={classes.required}> *</span>
            </p>

            <TextField
              className={classes.longInputField}
              value={values.projectName}
              id="project-name"
              name='projectName'
              variant="outlined"
              size='small'
              error={errors.projectName !== '' ? true : false}
              helperText={errors.projectName}
              required
              onChange={handleChangeValues}
              onBlur={handleValidate}
              inputProps={{ maxLength: 50 }}
            />
          </div>

          <div className={classes.formItem}>
            <p className={classes.label}>
              <Translate content="projectManagement.form.customer" />
              <span className={classes.required}> *</span>
            </p>

            <TextField
              className={classes.longInputField}
              value={values.customer}
              id="customer"
              name='customer'
              variant="outlined"
              size='small'
              error={errors.customer !== '' ? true : false}
              helperText={errors.customer}
              required
              onChange={handleChangeValues}
              onBlur={handleValidate}
            />
          </div>

          <div className={classes.formItem}>
            <p className={classes.label}>
              <Translate content="projectManagement.form.group.label" />
              <span className={classes.required}> *</span>
            </p>

            <Autocomplete
              value={values.group}
              className={classes.shortInputField}
              onChange={(event, newValue) => {
                if (typeof newValue === 'string') {
                  // timeout to avoid instant validation of the dialog's form.
                  setTimeout(() => {
                    setOpenCreateGroupDialog(true);
                    setGroupName(newValue);
                  });
                } else if (newValue && newValue.inputValue) {
                  setOpenCreateGroupDialog(true);
                  setGroupName(newValue.inputValue);
                } else {
                  setValues({
                    ...values,
                    group: newValue
                  });
                }
              }}
              filterOptions={(options, params) => {
                const filtered = filter(options, params);

                if (params.inputValue !== '') {
                  filtered.push({
                    inputValue: params.inputValue,
                    name:
                      <div>
                        <Translate content="projectManagement.form.group.add" />
                        <span> "{params.inputValue}"</span>
                      </div>
                  });
                }

                return filtered;
              }}
              id="combobox-group"
              name='group'
              size='small'
              options={groups}
              getOptionLabel={(option) => {
                // e.g value selected with enter, right from the input
                if (typeof option === 'string') {
                  return option;
                }
                if (option.inputValue) {
                  return option.inputValue;
                }
                return option.name;
              }}
              selectOnFocus
              clearOnBlur
              renderOption={(option) => option.name}
              renderInput={(params) =>
                <TextField
                  {...params}
                  name='group'
                  label={<Translate content="projectManagement.form.group.groupComboBoxLabel" />}
                  variant="outlined"
                  style={{ color: '#666666' }}
                  error={errors.group !== '' ? true : false}
                  helperText={errors.group}
                  onBlur={handleValidate}
                />
              }
            />
          </div>

          <div className={classes.formItem}>
            <p className={classes.label}>
              <Translate content="projectManagement.form.members" />
            </p>

            <Autocomplete
              multiple
              getOptionSelected={(option, value) => {
                return option.id === value.id;
              }}
              filterSelectedOptions
              value={values.members}
              className={classes.longInputField}
              id="members"
              name='members'
              size='small'
              onChange={(event, newValue) => {
                setValues({
                  ...values,
                  members: newValue
                });
              }}
              options={employees}
              getOptionLabel={(option) => {
                return option.visa + ": " + option.fullName;
              }}
              renderInput={(params) => (
                <TextField
                  {...params}
                  variant="outlined"
                />
              )}
            />
          </div>

          <div className={classes.formItem}>
            <p className={classes.label}>
              <Translate content="projectManagement.form.status" />
              <span className={classes.required}> *</span>
            </p>

            <Autocomplete
              value={values.status}
              className={classes.shortInputField}
              id="combo-box-project-status"
              size='small'
              onChange={(event, newValue) => setValues({
                ...values,
                status: newValue
              })}
              options={projectStatuses}
              getOptionLabel={(option) => option.title}
              renderInput={(params) =>
                <TextField
                  {...params}
                  name='status'
                  variant='outlined'
                  style={{ color: '#666666' }}
                  error={errors.status !== '' ? true : false}
                  helperText={errors.status}
                  onBlur={handleValidate}
                />
              }
            />
          </div>

          <div className={classes.formItem}>
            <p className={classes.label}>
              <Translate content="projectManagement.form.startDate" />
              <span className={classes.required}> *</span>
            </p>

            <MuiPickersUtilsProvider utils={MomentUtils}>
              <DatePicker
                className={classes.datePicker}
                value={values.startDate}
                id='start-date'
                name='startDate'
                size='small'
                variant='outlined'
                onChange={(date) => {
                  if (date === null) {
                    setErrors({
                      ...errors,
                      startDate: <Translate content="projectManagement.form.errors.emptyField" />
                    });
                    setValues({
                      ...values,
                      startDate: null
                    });
                  } else {
                    const isValid = moment(date, DATE_FORMAT, true).isValid();
                    if (isValid) {
                      setValues({
                        ...values,
                        startDate: date
                      });
                      setErrors({
                        ...errors,
                        startDate: ''
                      });
                    } else {
                      setValues({
                        ...values,
                        startDate: null
                      });
                      setErrors({
                        ...errors,
                        startDate: isValid ? "" : <Translate content="projectManagement.form.errors.invalidDate" />
                      });
                    }
                  }
                }}
                format={DATE_FORMAT}
                animateYearScrolling
                invalidDateMessage=""
                style={{ border: errors.startDate !== '' ? '1px solid red' : '' }}
              />
            </MuiPickersUtilsProvider>

            <p className={classes.label} style={{ marginLeft: 90, marginRight: -50, paddingTop: 5 }}>
              <Translate content="projectManagement.form.endDate" />
            </p>

            <MuiPickersUtilsProvider utils={MomentUtils}>
              <DatePicker
                className={classes.datePicker}
                value={values.endDate}
                id='end-date'
                name='endDate'
                size='small'
                variant='outlined'
                onChange={(date) => {
                  if (date !== null) {
                    const isValid = moment(date, DATE_FORMAT, true).isValid();
                    if (isValid) {
                      setValues({
                        ...values,
                        endDate: date
                      });
                      setErrors({
                        ...errors,
                        endDate: ''
                      });
                    } else {
                      setValues({
                        ...values,
                        endDate: null
                      });
                      setErrors({
                        ...errors,
                        endDate: isValid ? "" : <Translate content="projectManagement.form.errors.invalidDate" />
                      });
                    }
                  } else {
                    setValues({
                      ...values,
                      endDate: null
                    });
                    setErrors({
                      ...errors,
                      endDate: ''
                    });
                  }
                }}
                format={DATE_FORMAT}
                animateYearScrolling
                invalidDateMessage=""
                style={{ border: errors.endDate !== '' ? '1px solid red' : '' }}
              />
            </MuiPickersUtilsProvider>
          </div>

          <div className={classes.helperTextForDatePicker}>
            <p className={classes.errorLabel}>
              {errors.startDate}
            </p>

            <div className={classes.grow} />

            <p className={classes.errorLabel}>
              {errors.endDate}
            </p>
          </div>

          <br />
          <Divider />
          <br />

          <div className={classes.formItem}>
            <div className={classes.grow} />

            <Button
              className={classes.button}
              variant="outlined"
              style={{ backgroundColor: '#b9b9b9' }}
              onClick={handleCancel}
            >
              <Translate content="projectManagement.form.cancelButton" />
            </Button>

            <Button
              className={classes.button}
              type="submit"
              color="primary"
              variant="outlined"
              style={{ backgroundColor: '#2f85fa', color: 'white' }}
            >
              <Translate
                content={mode === "new" ? "projectManagement.form.createButton" : "projectManagement.form.updateButton"}
              />
            </Button>
          </div>

        </form>
      </div>

      <Dialog open={openCreateGroupDialog} onClose={handleCloseCreateGroupDialog} aria-labelledby="form-dialog-title">
        <form onSubmit={handleSubmitNewGroup}>
          <DialogTitle id="form-dialog-title">
            <Translate content="projectManagement.form.group.dialog.title" />
          </DialogTitle>
          <DialogContent>
            <DialogContentText>
              <Translate content="projectManagement.form.group.dialog.title" />
            </DialogContentText>
            <TextField
              autoFocus
              margin="dense"
              id="name"
              value={groupName}
              onChange={(event) => setGroupName(event.target.value)}
              label={<Translate content="projectManagement.form.group.dialog.label" />}
              type="text"
            />
          </DialogContent>
          <DialogActions>
            <Button onClick={handleCloseCreateGroupDialog} color="primary">
              <Translate content="projectManagement.form.group.dialog.cancelButton" />
            </Button>
            <Button type="submit" color="primary">
              <Translate content="projectManagement.form.group.dialog.addButton" />
            </Button>
          </DialogActions>
        </form>
      </Dialog>

      <Dialog
        open={openProcessWaitingDialog}
        onClose={handleCloseProcessWaitingDialog}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
      >
        <DialogTitle id="alert-dialog-title">
          <Translate content="projectManagement.form.working" />
        </DialogTitle>
        <DialogContent className={classes.dialogContent}>
          <CircularProgress style={{ textAlign: 'center' }} />
        </DialogContent>
      </Dialog>

      <Snackbar
        anchorOrigin={{ vertical: 'bottom', horizontal: 'left' }}
        open={openSnackbar}
        onClose={handleCloseSnackbar}
      >
        <Alert
          severity={isSuccessRequest ? "success" : "error"}
          color={isSuccessRequest ? "success" : "error"}
          variant="filled"
        >
          {codeAndMessage.get(code)}
        </Alert>
      </Snackbar>
    </React.Fragment>
  );
}
