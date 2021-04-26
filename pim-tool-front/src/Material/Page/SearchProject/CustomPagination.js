import React from 'react';
import { Pagination, PaginationItem } from '@material-ui/lab';

export default function CustomPagination({ currentPage, setCurrentPage, pageCount }) {
  return (
    <Pagination
      shape="rounded"
      color="primary"
      variant="outlined"
      count={pageCount}
      page={currentPage + 1}
      renderItem={(props) => <PaginationItem {...props} disableRipple />}
      onChange={(event, value) => {
        setCurrentPage(value - 1);
      }}
    />
  );
}