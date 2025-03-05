import http from "k6/http";
import { check, sleep } from "k6";

export const options = {
  stages: [
    { duration: "10s", target: 1 }, // calentar con 50 usuarios durante 10 segundos
    { duration: "10s", target: 1 }, // incrementar a 80 usuarios durante 10 segundos
  ],
};

export default function () {
  const res = http.post(
    "https://sharp-lisa-jhonmata0427s-projects-a5f958cc.koyeb.app/api/v1/auth/login",
    JSON.stringify({
      email: "jhonmata0427@gmail.com",
      password: "Jjmm04272003ce5.",
    }),
    {
      headers: {
        "Content-Type": "application/json",
      },
    }
  );

  check(res, {
    "Es estado 200": ({ status }) => status === 200,
    "Tiempo de respuesta < 1500ms": ({ timings }) => timings.duration < 1500,
    "Contenido de respuesta está en formato JSON": ({ headers }) =>
      headers["Content-Type"] === "application/json",
    "Se obtiene el token": (r) => !!r.json().token,
  });

  sleep(1);
}
