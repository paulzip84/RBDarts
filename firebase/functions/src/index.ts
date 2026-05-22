import { onCall, type CallableRequest } from "firebase-functions/v2/https";
import { createLeague } from "./leagueOperations.ts";
import { createMatch } from "./matchOperations.ts";
import { createStandaloneGame, submitStandaloneScore } from "./standaloneGame.ts";
import { safeError } from "./trustedOperations.ts";

function callable<TInput, TOutput>(handler: (request: CallableRequest<TInput>) => TOutput) {
  return onCall<TInput>((request) => {
    try {
      return handler(request);
    } catch (error) {
      return safeError(error);
    }
  });
}

export const createLeagueCallable = callable(createLeague);
export const createMatchCallable = callable(createMatch);
export const createStandaloneGameCallable = callable(createStandaloneGame);
export const submitStandaloneScoreCallable = callable(submitStandaloneScore);
