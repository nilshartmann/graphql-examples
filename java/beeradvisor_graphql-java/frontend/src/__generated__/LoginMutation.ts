/* tslint:disable */
// This file was automatically generated and should not be edited.

// ====================================================
// GraphQL mutation operation: LoginMutation
// ====================================================

export interface LoginMutation_login_authentication {
  userId: string;
  username: string;
  authToken: string;
}

export interface LoginMutation_login {
  authentication: LoginMutation_login_authentication | null;
  error: string | null;
}

export interface LoginMutation {
  login: LoginMutation_login;
}

export interface LoginMutationVariables {
  username: string;
}
