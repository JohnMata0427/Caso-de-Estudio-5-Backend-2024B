import http from "k6/http";
import { check } from "k6";

export const options = {
  stages: [
    { duration: "5s", target: 50 }, // calentar con 100 usuarios durante 5 segundos
    { duration: "10s", target: 50 }, // incrementar a 200 usuarios durante 10 segundos
  ],
};

const token =
  "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJqaG9ubWF0YTA0MjdAZ21haWwuY29tIiwiaWF0IjoxNzQxMTkzMjY5LCJleHAiOjE3NDEyMDc2Njl9.yOWzVDv0lyNs_E03pNHYG8MrwsPPj8Yi_SwTg4Qu3gCY5BVabJQxSdYRm8EiPtdT";

export default function () {
  const res = http.get(
    "https://sharp-lisa-jhonmata0427s-projects-a5f958cc.koyeb.app/api/v1/auth/profile",
    {
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    }
  );

  check(res, {
    "Es estado 200": ({ status }) => status === 200,
    "Tiempo de respuesta < 1s": ({ timings }) => timings.duration < 1000,
    "Contenido de respuesta está en formato JSON": ({ headers }) =>
      headers["Content-Type"] === "application/json",
    "Se obtiene el nombre del usuario": (r) => !!r.json().usuario.nombre,
  });
}
