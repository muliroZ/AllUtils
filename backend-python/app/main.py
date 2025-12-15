from fastapi import FastAPI
from .routes import qr, convert

app = FastAPI()

app.include_router(qr.router)
app.include_router(convert.router)

@app.get("/", tags=["Status"])
def read_root():
    return {"message": "API online."}