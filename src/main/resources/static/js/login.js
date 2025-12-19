document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("loginForm");
  const messageDiv = document.getElementById("message");

  form.addEventListener("submit", async (event) => {
    event.preventDefault();

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    const data = {
      email: email,
      senha: password,
    };

    try {
      const response = await fetch("/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
      });

      if (response.ok) {
        messageDiv.textContent = "Login realizado com sucesso!";
        messageDiv.style.color = "green";
        setTimeout(() => {
          window.location.href = "/index.html";
        }, 2000);
      } else {
        const errorResult = await response.json();
        messageDiv.textContent = errorResult.message || "Login falhou!";
        messageDiv.style.color = "red";
      }
    } catch (error) {
      alert("Ocorreu um erro: " + error.message);
    }
  });
});
