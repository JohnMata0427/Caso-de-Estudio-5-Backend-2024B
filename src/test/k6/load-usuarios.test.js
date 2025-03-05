import http from "k6/http";
import { check, sleep } from "k6";

export const options = {
	stages: [
		{ duration: "10s", target: 50 }, // calentar con 50 usuarios durante 10 segundos
		{ duration: "10s", target: 80 }, // incrementar a 80 usuarios durante 10 segundos
	],
};

export default function () {
	const res = http.post("http://localhost:3000/usuarios", {
    
  })

	check(res, {
		"Es estado 200": (r) => r.status === 200,
    "Tiempo de respuesta < 1s": (r) => r.timings.duration < 1000,
		"Contenido de respuesta está en formato JSON": (r) =>
			r.headers["Content-Type"] === "application/json",
	});
  
	console.log(`Tiempo de respuesta: ${res.timings.duration} ms`);

	sleep(1);
}