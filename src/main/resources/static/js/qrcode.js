document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("qrForm");
  const qrContainer = document.getElementById("qrcode");

  form.addEventListener("submit", async (e) => {
    e.preventDefault();

    const formData = new FormData(form);
    const textData = formData.get("text");

    if (!textData) {
      qrContainer.innerHTML =
        "<p style='color: red;'>Por favor, insira o texto para gerar o QR Code.</p>";
      return;
    }

    const response = await fetch("api/v1/qr/", {
      method: "POST",
      headers: { "Content-Type": "text/plain" },
      body: textData,
    });

    if (response.ok) {
      const blob = await response.blob();
      const imageUrl = URL.createObjectURL(blob);
      qrContainer.innerHTML = `<img src="${imageUrl}" alt="QR Code" />`;
    } else {
      const errorText = await response.text();
      qrContainer.innerHTML =
        "<p style='color: red;'>Erro ao gerar o QR Code. Tente novamente." +
        errorText +
        "</p>";
    }
  });
});
