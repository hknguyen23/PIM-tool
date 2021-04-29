import React, { useState } from 'react';
import { useHistory } from 'react-router-dom';
import {
  Button,
  Select,
  MenuItem,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Snackbar
} from '@material-ui/core';
import Alert from '@material-ui/lab/Alert';
import DeleteForeverIcon from '@material-ui/icons/DeleteForever';
import { makeStyles } from '@material-ui/core/styles';
import Translate from 'react-translate-component';
import CustomPagination from './CustomPagination';
import { deleteProject, getProjects } from '../../Services/projectService';
import codeAndMessage from '../../Constants/codeAndMessage';

const useStyles = makeStyles((theme) => ({
  button: {
    padding: 0,
    borderRadius: '4px',
    textTransform: 'none',
    width: 350,
    height: '40px'
  },
  deleteIcon: {
    color: 'red'
  },
  errorLabel: {
    paddingLeft: 14,
    paddingTop: 4,
    width: 350,
    fontSize: '10pt',
    fontFamily: 'Segoe UI',
    color: 'red',
    textAlign: 'center'
  }
}));

export default function CustomFooter({ filter, searchFieldValue, rowsSelected, setRowsSelected, pageSize, setPageSize, currentPage, setCurrentPage, pageCount, setPageCount, setIsLoading, setProjects }) {
  const classes = useStyles();
  const history = useHistory();
  const count = rowsSelected.length;
  const [isError, setIsError] = useState(false);
  const [openConfirmDeleteProjectDialog, setOpenConfirmDeleteProjectDialog] = useState(false);
  const [openSnackbar, setOpenSnackbar] = useState(false);
  const [isSuccessRequest, setIsSuccessRequest] = useState(false);
  const [code, setCode] = useState(0);

  const handleChangePageSize = (e) => {
    setPageSize(e.target.value);
  }

  const handleDeleteProjects = () => {
    const temp = rowsSelected.filter(row => row.status.value === 'NEW');
    if (temp.length !== rowsSelected.length) {
      setIsError(true);
      setOpenConfirmDeleteProjectDialog(false);
      return;
    }

    const ids = rowsSelected.map(row => row.id);
    deleteProject(ids)
      .then(res1 => {
        console.log(res1);
        const data = {
          currentPage,
          pageSize,
          status: filter.value,
          searchValue: searchFieldValue
        }
        if (res1.code === 200) {
          getProjects(data)
            .then(res2 => {
              console.log(res2);
              setCode(res2.code);
              if (res2.code === 200) {
                if (res2.data.currentPage >= res2.data.totalPages) {
                  setCurrentPage(res2.data.totalPages - 1);
                } else {
                  setCurrentPage(res2.data.currentPage);
                }
                setPageCount(res2.data.totalPages);
                setProjects(res2.data.data);
                setIsLoading(false);
                setRowsSelected([]);
                setIsSuccessRequest(true);
                setOpenSnackbar(true);
              } else {
                setIsLoading(true);
                setIsSuccessRequest(false);
                setProjects([]);
              }
            })
            .catch(error => {
              history.push("/error", { errorMessage: error });
              console.log(error);
            });
        } else {
          setCode(res1.code);
          setIsLoading(true);
          setIsSuccessRequest(false);
          setOpenSnackbar(true);
          setProjects([]);
          setCurrentPage(0);
        }
      })
      .catch(error => {
        history.push("/error", { errorMessage: error });
        console.log(error);
      });

    setOpenConfirmDeleteProjectDialog(false);
  }

  return (
    <React.Fragment>
      <br></br>
      {count === 0 ?
        <div className="MuiDataGrid-selectedRowCount">
          <Translate content="searchProject.table.noRowsSelected" />
        </div> :
        <div className="MuiDataGrid-selectedRowCount">
          {count}
          &nbsp;
          {count === 1
            ? <Translate content="searchProject.table.row" />
            : <Translate content="searchProject.table.rows" />
          }
          &nbsp;
          <Translate content="searchProject.table.selected" />
        </div>
      }
      <br></br>
      <div style={{ display: 'flex', alignItems: 'center' }}>
        <Button
          className={classes.button}
          size='small'
          variant="contained"
          disabled={count === 0}
          onClick={() => setOpenConfirmDeleteProjectDialog(true)}
        >
          <DeleteForeverIcon className={classes.deleteIcon} />
          <Translate content="searchProject.deleteAllButton" />
        </Button>
        <div style={{ flexGrow: 1 }}></div>

        <div style={{ marginRight: 10 }}>
          <Translate content="searchProject.table.rowsPerPage" />
        </div>
        <Select
          labelId="demo-simple-select-label"
          id="demo-simple-select"
          value={pageSize}
          style={{ marginRight: 10, width: 50 }}
          onChange={handleChangePageSize}
        >
          <MenuItem value={5}>5</MenuItem>
          <MenuItem value={10}>10</MenuItem>
          <MenuItem value={20}>20</MenuItem>
        </Select>

        <CustomPagination
          currentPage={currentPage}
          setCurrentPage={setCurrentPage}
          pageCount={pageCount}
        />

      </div>

      {isError
        ?
        <p className={classes.errorLabel}>
          <Translate content="searchProject.cannotDelete" />
        </p>
        :
        <p style={{ height: 25 }}></p>
      }

      <Dialog
        maxWidth="xs"
        aria-labelledby="confirmation-dialog-title"
        open={openConfirmDeleteProjectDialog}
      >
        <DialogTitle id="confirmation-dialog-title">
          <Translate content="searchProject.deleteConfirmDialog.title" />
        </DialogTitle>
        <DialogContent dividers>
          <Translate content="searchProject.deleteConfirmDialog.content" />
        </DialogContent>
        <DialogActions>
          <Button autoFocus onClick={() => setOpenConfirmDeleteProjectDialog(false)} color="primary">
            <Translate content="searchProject.deleteConfirmDialog.cancelButton" />
          </Button>
          <Button onClick={handleDeleteProjects} style={{ color: 'red' }}>
            <Translate content="searchProject.deleteConfirmDialog.deleteButton" />
          </Button>
        </DialogActions>
      </Dialog>

      <Snackbar
        anchorOrigin={{ vertical: 'bottom', horizontal: 'left' }}
        open={openSnackbar}
        onClose={() => setOpenSnackbar(false)}
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