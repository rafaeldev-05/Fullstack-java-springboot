const API_URL = "/api/fila";

const statusLabels = {
    AGUARDANDO: "Waiting",
    CHAMADO: "Called",
    ATENDIDO: "Served"
};

const serviceLabels = {
    Clinica: "Clinic",
    Barbearia: "Barbershop",
    "Assistencia tecnica": "Technical support",
    Secretaria: "Administrative office"
};

const formFila = document.getElementById("formFila");
const mensagem = document.getElementById("mensagem");
const corpoTabela = document.getElementById("corpoTabela");
const resumoFila = document.getElementById("resumoFila");
const btnAtualizar = document.getElementById("btnAtualizar");
const btnChamarProximo = document.getElementById("btnChamarProximo");

function mostrarMensagem(texto, tipo = "") {
    mensagem.textContent = texto;
    mensagem.className = `mensagem ${tipo}`.trim();
}

async function lerResposta(response) {
    if (response.status === 204) {
        return null;
    }

    const conteudo = await response.json();

    if (!response.ok) {
        const erro = conteudo.mensagens ? conteudo.mensagens.join(" ") : "Unexpected error.";
        throw new Error(erro);
    }

    return conteudo;
}

async function carregarFila() {
    try {
        const response = await fetch(API_URL);
        const pessoas = await lerResposta(response);
        desenharTabela(pessoas);
    } catch (error) {
        mostrarEstadoTabela(error.message);
    }
}

function mostrarEstadoTabela(texto) {
    corpoTabela.replaceChildren();
    const celula = corpoTabela.insertRow().insertCell();
    celula.colSpan = 6;
    celula.textContent = texto;
}

function desenharTabela(pessoas) {
    resumoFila.textContent = `${pessoas.length} registered`;

    if (pessoas.length === 0) {
        mostrarEstadoTabela("Nobody has joined the queue yet.");
        return;
    }

    corpoTabela.replaceChildren();
    pessoas.forEach((pessoa, index) => {
        const linha = corpoTabela.insertRow();
        const atendimento = Object.hasOwn(serviceLabels, pessoa.tipoAtendimento)
            ? serviceLabels[pessoa.tipoAtendimento]
            : pessoa.tipoAtendimento;

        for (const valor of [index + 1, pessoa.nome, pessoa.telefone, atendimento]) {
            linha.insertCell().textContent = valor;
        }

        const status = document.createElement("span");
        status.className = "status";
        if (Object.hasOwn(statusLabels, pessoa.status)) {
            status.classList.add(pessoa.status);
            status.textContent = statusLabels[pessoa.status];
        }
        linha.insertCell().append(status);

        const acoes = document.createElement("div");
        acoes.className = "acoes";
        if (pessoa.status === "CHAMADO") {
            acoes.append(criarBotao("Mark as served", "botao pequeno", () => marcarAtendido(pessoa.id)));
        }
        acoes.append(criarBotao("Remove", "botao perigo pequeno", () => removerPessoa(pessoa.id)));
        linha.insertCell().append(acoes);
    });
}

function criarBotao(texto, classe, acao) {
    const botao = document.createElement("button");
    botao.type = "button";
    botao.className = classe;
    botao.textContent = texto;
    botao.addEventListener("click", acao);
    return botao;
}

formFila.addEventListener("submit", async (event) => {
    event.preventDefault();

    const dados = {
        nome: formFila.nome.value,
        telefone: formFila.telefone.value,
        tipoAtendimento: formFila.tipoAtendimento.value
    };

    try {
        const response = await fetch(API_URL, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(dados)
        });

        await lerResposta(response);
        formFila.reset();
        mostrarMensagem("Person added to the queue.", "sucesso");
        carregarFila();
    } catch (error) {
        mostrarMensagem(error.message, "erro");
    }
});

btnChamarProximo.addEventListener("click", async () => {
    try {
        const response = await fetch(`${API_URL}/chamar-proximo`, { method: "PATCH" });
        const pessoa = await lerResposta(response);
        mostrarMensagem(`${pessoa.nome} has been called.`, "sucesso");
        carregarFila();
    } catch (error) {
        mostrarMensagem(error.message, "erro");
    }
});

btnAtualizar.addEventListener("click", carregarFila);

async function marcarAtendido(id) {
    try {
        const response = await fetch(`${API_URL}/${id}/atendido`, { method: "PATCH" });
        await lerResposta(response);
        mostrarMensagem("Service completed.", "sucesso");
        carregarFila();
    } catch (error) {
        mostrarMensagem(error.message, "erro");
    }
}

async function removerPessoa(id) {
    try {
        const response = await fetch(`${API_URL}/${id}`, { method: "DELETE" });
        await lerResposta(response);
        mostrarMensagem("Person removed from the queue.", "sucesso");
        carregarFila();
    } catch (error) {
        mostrarMensagem(error.message, "erro");
    }
}

carregarFila();
