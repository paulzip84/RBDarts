import type { CallableRequest } from "firebase-functions/v2/https";

export type TrustedOperationErrorCode =
  | "unauthenticated"
  | "permission_denied"
  | "validation_failed"
  | "conflict"
  | "not_found"
  | "retryable_unavailable"
  | "internal_safe";

export class TrustedOperationError extends Error {
  public readonly code: TrustedOperationErrorCode;

  constructor(code: TrustedOperationErrorCode, message: string) {
    super(message);
    this.code = code;
    this.name = "TrustedOperationError";
  }
}

export interface AuthenticatedContext {
  uid: string;
}

export function requireAuth(request: CallableRequest<unknown>): AuthenticatedContext {
  if (!request.auth?.uid) {
    throw new TrustedOperationError("unauthenticated", "Please sign in to continue.");
  }
  return { uid: request.auth.uid };
}

export function requireNonEmpty(value: unknown, fieldName: string): string {
  if (typeof value !== "string" || value.trim().length === 0) {
    throw new TrustedOperationError("validation_failed", `${fieldName} is required.`);
  }
  return value.trim();
}

export function requireScore(value: unknown): number {
  if (!Number.isInteger(value) || (value as number) < 0 || (value as number) > 9) {
    throw new TrustedOperationError("validation_failed", "Scores must be whole numbers from 0 through 9.");
  }
  return value as number;
}

export function safeError(error: unknown): { code: TrustedOperationErrorCode; message: string } {
  if (error instanceof TrustedOperationError) {
    return { code: error.code, message: error.message };
  }
  return { code: "internal_safe", message: "Unable to complete that request right now." };
}
