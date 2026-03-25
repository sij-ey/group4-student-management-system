// UniReg - app.js
// Minor UX enhancements for the Student Management System

document.addEventListener('DOMContentLoaded', () => {

    // Auto-dismiss flash alerts after 5 seconds
    document.querySelectorAll('.alert.alert-success, .alert.alert-danger').forEach(alert => {
        setTimeout(() => {
            const bsAlert = bootstrap.Alert.getOrCreateInstance(alert);
            if (bsAlert) bsAlert.close();
        }, 5000);
    });

    // Add active class to current nav link based on URL
    const currentPath = window.location.pathname;
    document.querySelectorAll('#mainNav .nav-link').forEach(link => {
        const href = link.getAttribute('href');
        if (href && currentPath.startsWith(href) && href !== '/') {
            link.classList.add('active');
        }
    });

    // Confirm on all delete/drop buttons (data-confirm attribute override)
    document.querySelectorAll('[data-confirm]').forEach(el => {
        el.addEventListener('click', e => {
            if (!confirm(el.dataset.confirm)) e.preventDefault();
        });
    });

    // Animate stat card numbers on dashboard
    document.querySelectorAll('.stat-card__value').forEach(el => {
        const target = parseInt(el.textContent, 10);
        if (isNaN(target) || target === 0) return;
        let current = 0;
        const step = Math.max(1, Math.floor(target / 30));
        const timer = setInterval(() => {
            current = Math.min(current + step, target);
            el.textContent = current;
            if (current >= target) clearInterval(timer);
        }, 25);
    });
});
