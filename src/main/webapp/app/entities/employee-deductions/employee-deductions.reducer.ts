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

import { IEmployeeDeductions, defaultValue } from 'app/shared/model/employee-deductions.model';

export const ACTION_TYPES = {
  FETCH_EMPLOYEEDEDUCTIONS_LIST: 'employeeDeductions/FETCH_EMPLOYEEDEDUCTIONS_LIST',
  FETCH_EMPLOYEEDEDUCTIONS: 'employeeDeductions/FETCH_EMPLOYEEDEDUCTIONS',
  CREATE_EMPLOYEEDEDUCTIONS: 'employeeDeductions/CREATE_EMPLOYEEDEDUCTIONS',
  UPDATE_EMPLOYEEDEDUCTIONS: 'employeeDeductions/UPDATE_EMPLOYEEDEDUCTIONS',
  DELETE_EMPLOYEEDEDUCTIONS: 'employeeDeductions/DELETE_EMPLOYEEDEDUCTIONS',
  RESET: 'employeeDeductions/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEmployeeDeductions>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type EmployeeDeductionsState = Readonly<typeof initialState>;

// Reducer

export default (state: EmployeeDeductionsState = initialState, action): EmployeeDeductionsState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EMPLOYEEDEDUCTIONS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EMPLOYEEDEDUCTIONS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_EMPLOYEEDEDUCTIONS):
    case REQUEST(ACTION_TYPES.UPDATE_EMPLOYEEDEDUCTIONS):
    case REQUEST(ACTION_TYPES.DELETE_EMPLOYEEDEDUCTIONS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_EMPLOYEEDEDUCTIONS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EMPLOYEEDEDUCTIONS):
    case FAILURE(ACTION_TYPES.CREATE_EMPLOYEEDEDUCTIONS):
    case FAILURE(ACTION_TYPES.UPDATE_EMPLOYEEDEDUCTIONS):
    case FAILURE(ACTION_TYPES.DELETE_EMPLOYEEDEDUCTIONS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMPLOYEEDEDUCTIONS_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_EMPLOYEEDEDUCTIONS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_EMPLOYEEDEDUCTIONS):
    case SUCCESS(ACTION_TYPES.UPDATE_EMPLOYEEDEDUCTIONS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_EMPLOYEEDEDUCTIONS):
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

const apiUrl = 'api/employee-deductions';

// Actions

export const getEntities: ICrudGetAllAction<IEmployeeDeductions> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_EMPLOYEEDEDUCTIONS_LIST,
    payload: axios.get<IEmployeeDeductions>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IEmployeeDeductions> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EMPLOYEEDEDUCTIONS,
    payload: axios.get<IEmployeeDeductions>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IEmployeeDeductions> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EMPLOYEEDEDUCTIONS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IEmployeeDeductions> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EMPLOYEEDEDUCTIONS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEmployeeDeductions> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EMPLOYEEDEDUCTIONS,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
