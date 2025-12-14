from fastapi import FastAPI
from routes import qr, convert

app = FastAPI()

app.include_router(qr.router)
