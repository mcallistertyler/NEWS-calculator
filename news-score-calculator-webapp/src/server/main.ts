import express from "express";
import ViteExpress from "vite-express";

const app = express();

app.use(express.json());
const nodeEnv = process.env.NODE_ENV || "";
const env =  nodeEnv == "production" ? "production" : "development";

ViteExpress.config({ mode: env })

app.post("/news", async (req, res) => {
  try {
    const response = await fetch("http://localhost:8080/news", {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(req.body)
    });


    const data = await response.json();

    res.status(200).json(data);

  } catch (error) {
    console.error("Something went wrong", error);
  }
});

ViteExpress.listen(app, 3000, () =>
  console.log("Server is listening on port 3000..."),
);
