import axios from 'axios';
import {
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEmployeeTimeSheet, defaultValue } from 'app/shared/model/employee-time-sheet.model';

export const ACTION_TYPES = {
  FETCH_EMPLOYEETIMESHEET_LIST: 'employeeTimeSheet/FETCH_EMPLOYEETIMESHEET_LIST',
  FETCH_EMPLOYEETIMESHEET: 'employeeTimeSheet/FETCH_EMPLOYEETIMESHEET',
  CREATE_EMPLOYEETIMESHEET: 'employeeTimeSheet/CREATE_EMPLOYEETIMESHEET',
  UPDATE_EMPLOYEETIMESHEET: 'employeeTimeSheet/UPDATE_EMPLOYEETIMESHEET',
  DELETE_EMPLOYEETIMESHEET: 'employeeTimeSheet/DELETE_EMPLOYEETIMESHEET',
  RESET: 'employeeTimeSheet/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEmployeeTimeSheet>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type EmployeeTimeSheetState = Readonly<typeof initialState>;

// Reducer

export default (state: EmployeeTimeSheetState = initialState, action): EmployeeTimeSheetState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EMPLOYEETIMESHEET_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EMPLOYEETIMESHEET):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_EMPLOYEETIMESHEET):
    case REQUEST(ACTION_TYPES.UPDATE_EMPLOYEETIMESHEET):
    case REQUEST(ACTION_TYPES.DELETE_EMPLOYEETIMESHEET):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_EMPLOYEETIMESHEET_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EMPLOYEETIMESHEET):
    case FAILURE(ACTION_TYPES.CREATE_EMPLOYEETIMESHEET):
    case FAILURE(ACTION_TYPES.UPDATE_EMPLOYEETIMESHEET):
    case FAILURE(ACTION_TYPES.DELETE_EMPLOYEETIMESHEET):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMPLOYEETIMESHEET_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_EMPLOYEETIMESHEET):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_EMPLOYEETIMESHEET):
    case SUCCESS(ACTION_TYPES.UPDATE_EMPLOYEETIMESHEET):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_EMPLOYEETIMESHEET):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/employee-time-sheets';

// Actions

export const getEntities: ICrudGetAllAction<IEmployeeTimeSheet> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_EMPLOYEETIMESHEET_LIST,
    payload: axios.get<IEmployeeTimeSheet>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IEmployeeTimeSheet> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EMPLOYEETIMESHEET,
    payload: axios.get<IEmployeeTimeSheet>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IEmployeeTimeSheet> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EMPLOYEETIMESHEET,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IEmployeeTimeSheet> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EMPLOYEETIMESHEET,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEmployeeTimeSheet> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EMPLOYEETIMESHEET,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
