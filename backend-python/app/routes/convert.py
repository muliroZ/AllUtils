from fastapi import APIRouter, Response
from services.convert_service import converter

router = APIRouter(prefix="convert", tags=["Conversor"])

@router.post("/")
def converter():
    return