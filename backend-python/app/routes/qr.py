from fastapi import APIRouter, Response
from ..services.qr_service import gerar_qr

router = APIRouter(prefix="/qr", tags=["QR Code"])

@router.post("/")
def gerar(texto: str):
    img_bytes = gerar_qr(texto)
    return Response(content=img_bytes, media_type="image/png")
