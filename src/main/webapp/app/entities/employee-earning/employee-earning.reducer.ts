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

import { IEmployeeEarning, defaultValue } from 'app/shared/model/employee-earning.model';

export const ACTION_TYPES = {
  FETCH_EMPLOYEEEARNING_LIST: 'employeeEarning/FETCH_EMPLOYEEEARNING_LIST',
  FETCH_EMPLOYEEEARNING: 'employeeEarning/FETCH_EMPLOYEEEARNING',
  CREATE_EMPLOYEEEARNING: 'employeeEarning/CREATE_EMPLOYEEEARNING',
  UPDATE_EMPLOYEEEARNING: 'employeeEarning/UPDATE_EMPLOYEEEARNING',
  DELETE_EMPLOYEEEARNING: 'employeeEarning/DELETE_EMPLOYEEEARNING',
  RESET: 'employeeEarning/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEmployeeEarning>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type EmployeeEarningState = Readonly<typeof initialState>;

// Reducer

export default (state: EmployeeEarningState = initialState, action): EmployeeEarningState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EMPLOYEEEARNING_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EMPLOYEEEARNING):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_EMPLOYEEEARNING):
    case REQUEST(ACTION_TYPES.UPDATE_EMPLOYEEEARNING):
    case REQUEST(ACTION_TYPES.DELETE_EMPLOYEEEARNING):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_EMPLOYEEEARNING_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EMPLOYEEEARNING):
    case FAILURE(ACTION_TYPES.CREATE_EMPLOYEEEARNING):
    case FAILURE(ACTION_TYPES.UPDATE_EMPLOYEEEARNING):
    case FAILURE(ACTION_TYPES.DELETE_EMPLOYEEEARNING):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMPLOYEEEARNING_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_EMPLOYEEEARNING):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_EMPLOYEEEARNING):
    case SUCCESS(ACTION_TYPES.UPDATE_EMPLOYEEEARNING):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_EMPLOYEEEARNING):
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

const apiUrl = 'api/employee-earnings';

// Actions

export const getEntities: ICrudGetAllAction<IEmployeeEarning> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_EMPLOYEEEARNING_LIST,
    payload: axios.get<IEmployeeEarning>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IEmployeeEarning> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EMPLOYEEEARNING,
    payload: axios.get<IEmployeeEarning>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IEmployeeEarning> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EMPLOYEEEARNING,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IEmployeeEarning> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EMPLOYEEEARNING,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEmployeeEarning> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EMPLOYEEEARNING,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
