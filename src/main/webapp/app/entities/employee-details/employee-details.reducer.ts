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

import { IEmployeeDetails, defaultValue } from 'app/shared/model/employee-details.model';

export const ACTION_TYPES = {
  FETCH_EMPLOYEEDETAILS_LIST: 'employeeDetails/FETCH_EMPLOYEEDETAILS_LIST',
  FETCH_EMPLOYEEDETAILS: 'employeeDetails/FETCH_EMPLOYEEDETAILS',
  CREATE_EMPLOYEEDETAILS: 'employeeDetails/CREATE_EMPLOYEEDETAILS',
  UPDATE_EMPLOYEEDETAILS: 'employeeDetails/UPDATE_EMPLOYEEDETAILS',
  DELETE_EMPLOYEEDETAILS: 'employeeDetails/DELETE_EMPLOYEEDETAILS',
  RESET: 'employeeDetails/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEmployeeDetails>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type EmployeeDetailsState = Readonly<typeof initialState>;

// Reducer

export default (state: EmployeeDetailsState = initialState, action): EmployeeDetailsState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EMPLOYEEDETAILS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EMPLOYEEDETAILS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_EMPLOYEEDETAILS):
    case REQUEST(ACTION_TYPES.UPDATE_EMPLOYEEDETAILS):
    case REQUEST(ACTION_TYPES.DELETE_EMPLOYEEDETAILS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_EMPLOYEEDETAILS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EMPLOYEEDETAILS):
    case FAILURE(ACTION_TYPES.CREATE_EMPLOYEEDETAILS):
    case FAILURE(ACTION_TYPES.UPDATE_EMPLOYEEDETAILS):
    case FAILURE(ACTION_TYPES.DELETE_EMPLOYEEDETAILS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMPLOYEEDETAILS_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_EMPLOYEEDETAILS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_EMPLOYEEDETAILS):
    case SUCCESS(ACTION_TYPES.UPDATE_EMPLOYEEDETAILS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_EMPLOYEEDETAILS):
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

const apiUrl = 'api/employee-details';

// Actions

export const getEntities: ICrudGetAllAction<IEmployeeDetails> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_EMPLOYEEDETAILS_LIST,
    payload: axios.get<IEmployeeDetails>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IEmployeeDetails> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EMPLOYEEDETAILS,
    payload: axios.get<IEmployeeDetails>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IEmployeeDetails> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EMPLOYEEDETAILS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IEmployeeDetails> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EMPLOYEEDETAILS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEmployeeDetails> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EMPLOYEEDETAILS,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
