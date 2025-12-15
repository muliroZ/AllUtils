from fastapi import APIRouter, File, UploadFile, HTTPException
from fastapi.responses import StreamingResponse
import io
import os
from ..services.convert_service import convert_pdf_to_docx

router = APIRouter(prefix="/convert", tags=["Conversão de Arquivos"])

@router.post("/pdf-to-docx")
async def converter(file: UploadFile = File(..., description="PDF a ser convertido")):
    
    if file.content_type != "application/pdf":
        raise HTTPException(status_code=400, detail="O arquivo deve ser do tipo PDF")
    
    try:
        pdf_content = await file.read()
        docx_content, temp_docx_filename = convert_pdf_to_docx(pdf_content)

        output_filename_base: str = os.path.splitext(file.filename)[0] or "converted_document" # type: ignore
        final_filename = f"{output_filename_base}.docx"

        return StreamingResponse(
            io.BytesIO(docx_content),
            media_type="application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            headers={"Content-Disposition": f"attachment; filename=\"{final_filename}\""}
        )
    except Exception as e:
        print(f"Erro na requisição: {e}")
        raise HTTPException(status_code=500, detail="Erro interno ao processar a requisição.")