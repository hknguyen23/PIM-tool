import React, { useEffect, useState } from 'react';
import moment from 'moment';
import { useHistory, Link } from 'react-router-dom';
import Translate from 'react-translate-component';
import { makeStyles } from '@material-ui/core/styles';
import { DataGrid } from '@material-ui/data-grid';
import {
  Divider,
  TextField,
  Toolbar,
  Button,
  IconButton
} from '@material-ui/core';
import { Autocomplete } from '@material-ui/lab';
import DeleteForeverIcon from '@material-ui/icons/DeleteForever';
import CustomCheckbox from './CustomCheckbox';
import CustomNoRowsOverlay from './CustomNoRowsOverlay';
import CustomLoadingOverlay from './CustomLoadingOverlay';
import CustomFooter from './CustomFooter';
import { DATE_FORMAT, PAGE_SIZE } from '../../Constants/config.json';
import { getProjects, deleteProject } from '../../Services/projectService';

const projectStatuses = [
  { value: 'NEW', title: 'New' },
  { value: 'PLA', title: 'Planned' },
  { value: 'INP', title: 'In process' },
  { value: 'FIN', title: 'Finished' }
];

const useStyles = makeStyles((theme) => ({
  root: {
    border: 0,
    color:
      theme.palette.type === 'light' ? 'rgba(0,0,0,.85)' : 'rgba(255,255,255,0.85)',
    fontFamily: [
      '-apple-system',
      'BlinkMacSystemFont',
      '"Segoe UI"',
      'Roboto',
      '"Helvetica Neue"',
      'Arial',
      'sans-serif',
      '"Apple Color Emoji"',
      '"Segoe UI Emoji"',
      '"Segoe UI Symbol"',
    ].join(','),
    fontSize: '12pt',
    WebkitFontSmoothing: 'auto',
    letterSpacing: 'normal',
    '& .MuiDataGrid-columnsContainer': {
      backgroundColor: theme.palette.type === 'light' ? '#fafafa' : '#1d1d1d',
    },
    '& .MuiDataGrid-iconSeparator': {
      display: 'none',
    },
    '& .MuiDataGrid-colCell, .MuiDataGrid-cell': {
      borderRight: `1px solid ${theme.palette.type === 'light' ? '#f0f0f0' : '#303030'
        }`,
    },
    '& .MuiDataGrid-colCellTitle': {
      display: 'block',
      textAlign: 'right',
    },
    '& .MuiDataGrid-columnsContainer, .MuiDataGrid-cell': {
      borderBottom: `1px solid ${theme.palette.type === 'light' ? '#f0f0f0' : '#303030'
        }`,
    },
    '& .MuiDataGrid-cell': {
      color:
        theme.palette.type === 'light'
          ? 'rgba(0,0,0,.85)'
          : 'rgba(255,255,255,0.65)',
    },
    '& .MuiPaginationItem-root': {
      borderRadius: 0,
    },
    '& .MuiDataGrid-main': {
      border: '1px solid #ccc'
    },
    ...CustomCheckbox(theme),
  },
  label: {
    marginTop: theme.spacing(1),
  },
  toolbar: {
    padding: 0,
    marginTop: 20,
    marginBottom: 20
  },
  textField: {
    marginRight: 10,
    height: '40px'
  },
  comboBox: {
    height: '40px'
  },
  button: {
    marginLeft: 10,
    padding: 0,
    borderRadius: '4px',
    textTransform: 'none',
    width: '300px',
    height: '40px'
  },
  deleteIcon: {
    color: 'red'
  },
}));

export default function SearchProject() {
  const classes = useStyles();
  const history = useHistory();
  const FILTER_VALUE = localStorage.getItem('filter.value') || '';
  const FILTER_TITLE = localStorage.getItem('filter.title') || '';
  const SEARCH_VALUE = localStorage.getItem('searchValue') || '';

  const [isLoading, setIsLoading] = useState(true);
  const [rowsSelected, setRowsSelected] = useState([]);
  const [projects, setProjects] = useState([]);
  const [pageSize, setPageSize] = useState(PAGE_SIZE);
  const [currentPage, setCurrentPage] = useState(0);
  const [pageCount, setPageCount] = useState(0);
  const [filter, setFilter] = useState({
    value: FILTER_VALUE,
    title: FILTER_TITLE
  });
  const [searchFieldValue, setSearchFieldValue] = useState(SEARCH_VALUE);

  const columns = [
    {
      field: 'projectNumber',
      headerName: <Translate content="searchProject.table.numberColumn" />,
      width: 120,
      sortable: false,
      headerAlign: 'right',
      align: 'right',
      type: 'number',
      renderCell: (params) => {
        return <Link style={{ flex: 1 }} to={{
          pathname: "/projects/update/" + params.row.id,
          state: { currentProject: params.row }
        }}>
          {params.getValue('projectNumber')}
        </Link>
      }
    },
    { field: 'projectName', headerName: <Translate content="searchProject.table.nameColumn" />, width: 320, sortable: false },
    {
      field: 'status',
      headerName: <Translate content="searchProject.table.statusColumn" />,
      width: 120,
      sortable: false,
      valueFormatter: (params) => {
        return params.getValue('status').title;
      }
    },
    { field: 'customer', headerName: <Translate content="searchProject.table.customerColumn" />, width: 200, sortable: false },
    {
      field: 'startDate',
      headerName: <Translate content="searchProject.table.startDateColumn" />,
      type: 'date',
      width: 210,
      headerAlign: 'center',
      align: 'center',
      sortable: false,
      valueFormatter: (params) => {
        return moment(params.getValue('startDate')).format(DATE_FORMAT);
      }
    },
    {
      field: 'delete',
      headerName: <Translate content="searchProject.table.deleteColumn" />,
      flex: 1,
      sortable: false,
      headerAlign: 'center',
      align: 'center',
      renderCell: (params) => {
        // const onClick = () => {
        //   const api = params.api;
        //   const fields = api
        //     .getAllColumns()
        //     .map((c) => c.field)
        //     .filter((c) => c !== "__check__" && !!c);
        //   const thisRow = {};

        //   fields.forEach((f) => {
        //     thisRow[f] = params.getValue(f);
        //   });

        //   return alert(JSON.stringify(thisRow, null, 2));
        // }

        const handleDelete = () => {
          deleteProject([params.row.id])
            .then(res => {
              console.log(res);
              const data = {
                currentPage,
                pageSize,
                status: filter.value,
                searchValue: searchFieldValue
              }
              getProjects(data)
                .then(res => {
                  console.log(res);
                  setIsLoading(true);
                  if (res.code === 200) {
                    if (res.data.currentPage === res.data.totalPages) {
                      setCurrentPage(res.data.currentPage - 1);
                    } else {
                      setCurrentPage(res.data.currentPage);
                    }
                    setPageCount(res.data.totalPages);
                    setProjects(res.data.data);
                    setIsLoading(false);
                    setRowsSelected([]);
                  } else {
                    history.push("/error", { errorMessage: res.message });
                    setProjects([]);
                  }
                })
                .catch(error => {
                  console.log(error);
                });
            })
            .catch(error => {
              console.log(error);
            });
        }

        return params.row.status.value === 'NEW'
          ? <IconButton onClick={handleDelete}><DeleteForeverIcon className={classes.deleteIcon} /></IconButton>
          : <React.Fragment></React.Fragment>;
      }
    }
  ];

  // called when URL changed
  history.listen(() => {
    localStorage.setItem('filter.value', filter.value);
    localStorage.setItem('filter.title', filter.title);
    localStorage.setItem('searchValue', searchFieldValue);
  });

  useEffect(() => {
    const data = {
      currentPage,
      pageSize,
      status: filter.value,
      searchValue: searchFieldValue
    }
    getProjects(data)
      .then(res => {
        console.log(res);
        if (res.code === 200) {
          setCurrentPage(res.data.currentPage);
          setPageCount(res.data.totalPages);
          setProjects(res.data.data);
          setIsLoading(false);
          setRowsSelected([]);
        } else {
          history.push("/error", { errorMessage: res.message });
          setProjects([]);
        }
      })
      .catch(error => {
        console.log(error);
      });
  }, [currentPage, pageSize]);

  const handleSearch = () => {
    const data = {
      currentPage,
      pageSize,
      status: filter.value,
      searchValue: searchFieldValue
    }
    getProjects(data)
      .then(res => {
        console.log(res);
        if (res.code === 200) {
          setCurrentPage(res.data.currentPage);
          setPageCount(res.data.totalPages);
          setProjects(res.data.data);
          setIsLoading(false);
          setRowsSelected([]);
        } else {
          history.push("/error", { errorMessage: res.message });
          setProjects([]);
        }
      })
      .catch(error => {
        console.log(error);
      });
  }

  const handleReset = () => {
    setSearchFieldValue('');
    setFilter({
      value: '',
      title: ''
    });
    const data = {
      currentPage,
      pageSize,
      status: '',
      searchValue: ''
    }
    getProjects(data)
      .then(res => {
        console.log(res);
        if (res.code === 200) {
          setCurrentPage(res.data.currentPage);
          setPageCount(res.data.totalPages);
          setProjects(res.data.data);
          setIsLoading(false);
          setRowsSelected([]);
        } else {
          history.push("/error", { errorMessage: res.message });
          setProjects([]);
        }
      })
      .catch(error => {
        console.log(error);
      });
  }

  return (
    <React.Fragment>
      <p style={{ fontSize: '18pt', color: '#666666', fontWeight: 500, }}>
        <Translate content="searchProject.title" with={{ name: "Martin" }} />
      </p>

      <Divider />

      <div>
        <Toolbar className={classes.toolbar}>
          <TextField
            value={searchFieldValue}
            className={classes.textField}
            id="projectSearch"
            size='small'
            label={<Translate content="searchProject.searchFieldLabel" />}
            variant="outlined"
            fullWidth
            onChange={e => setSearchFieldValue(e.target.value)}
          />

          <Autocomplete
            value={filter}
            className={classes.comboBox}
            id="combo-box-project-status"
            size='small'
            options={projectStatuses}
            getOptionLabel={(option) => option.title}
            style={{ width: 400 }}
            onChange={(event, newValue) => {
              setFilter(newValue === null
                ? { value: '', title: '' }
                : newValue
              );
              const data = {
                currentPage,
                pageSize,
                status: newValue === null ? '' : newValue.value,
                searchValue: searchFieldValue
              }
              getProjects(data)
                .then(res => {
                  console.log(res);
                  if (res.code === 200) {
                    if (res.data.currentPage >= res.data.totalPages && res.data.currentPage !== 0) {
                      setCurrentPage(res.data.totalPages - 1);
                    } else {
                      setCurrentPage(res.data.currentPage);
                    }
                    setPageCount(res.data.totalPages);
                    setProjects(res.data.data);
                    setIsLoading(false);
                    setRowsSelected([]);
                  } else {
                    history.push("/error", { errorMessage: res.message });
                    setProjects([]);
                  }
                })
                .catch(error => {
                  console.log(error);
                });
            }}
            renderInput={(params) =>
              <TextField {...params} label={<Translate content="searchProject.comboBoxLabel" />} variant="outlined" />
            }
          />

          <Button className={classes.button} variant="contained" color="primary" onClick={handleSearch}>
            <Translate content="searchProject.searchButton"></Translate>
          </Button>

          <Button className={classes.button} variant="outlined" color="primary" onClick={handleReset}>
            <Translate content="searchProject.resetButton"></Translate>
          </Button>
        </Toolbar>
      </div >

      <div style={{ height: 190 + 41 * pageSize, width: '100%' }}>
        <DataGrid
          className={classes.root}
          components={{
            NoRowsOverlay: CustomNoRowsOverlay,
            LoadingOverlay: CustomLoadingOverlay,
            Footer: () =>
              <CustomFooter
                filter={filter}
                searchFieldValue={searchFieldValue}
                rowsSelected={rowsSelected}
                setRowsSelected={setRowsSelected}
                pageSize={pageSize}
                setPageSize={setPageSize}
                currentPage={currentPage}
                setCurrentPage={setCurrentPage}
                pageCount={pageCount}
                setPageCount={setPageCount}
                setIsLoading={setIsLoading}
                setProjects={setProjects}
              />
          }}
          loading={isLoading}
          disableSelectionOnClick
          disableColumnMenu
          rows={projects}
          columns={columns}
          pageSize={pageSize}
          checkboxSelection
          rowHeight={32}
          headerHeight={32}
          density='comfortable'
          onSelectionModelChange={(params) => {
            let temp = [];
            params.selectionModel.forEach(id => {
              temp = temp.concat(projects.filter(project => project.id === Number(id)));
            });
            setRowsSelected(temp);
          }}
        />
      </div>
    </React.Fragment>
  );
}
