import http from "k6/http";
import { check } from "k6";

export const options = {
  stages: [
    { duration: "10s", target: 1 }, // calentar con 1 usuarios durante 10 segundos
    { duration: "10s", target: 1 }, // incrementar a 1 usuarios durante 10 segundos
  ],
};

const token = ""

export default function () {
  const res = http.post(
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
