import React from 'react';
import Translate from 'react-translate-component';

const codeAndMessage = new Map();

codeAndMessage.set(200, <Translate content="messages.success" />);
codeAndMessage.set(500, <Translate content="messages.fail" />);

codeAndMessage.set(101, <Translate content="messages.projectNumberAlreadyExistsPleaseChooseAnother" />);
codeAndMessage.set(102, <Translate content="messages.visasDoNotExist" />);
codeAndMessage.set(103, <Translate content="messages.versionMissMatched" />);

export default codeAndMessage;

