import React from 'react';
import { Loading as StyledLoading } from './Loading.styles';

function Loading() {
  return (
    <StyledLoading>
      <StyledLoading.Spinner />
    </StyledLoading>
  );
}

export default Loading; 